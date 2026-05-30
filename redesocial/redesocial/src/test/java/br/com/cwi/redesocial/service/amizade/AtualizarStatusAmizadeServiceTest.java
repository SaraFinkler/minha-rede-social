package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.controller.request.amizade.AtualizarStatusAmizadeRequest;
import br.com.cwi.redesocial.controller.response.amizade.AmizadeResponse;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.StatusAmizade;
import br.com.cwi.redesocial.factory.AmizadeFactory;
import br.com.cwi.redesocial.factory.AtualizarStatusAmizadeRequestFactory;
import br.com.cwi.redesocial.factory.UsuarioFactory;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import br.com.cwi.redesocial.validator.amizade.AtualizarStatusValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para AtualizarStatusAmizadeService")
class AtualizarStatusAmizadeServiceTest {

    @Mock
    private AmizadeService amizadeService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private AmizadeRepository amizadeRepository;

    @Mock
    private AtualizarStatusValidator atualizarStatusValidator;

    @InjectMocks
    private AtualizarStatusAmizadeService atualizarStatusAmizadeService;

    private Amizade amizade;
    private Usuario usuario;
    private AtualizarStatusAmizadeRequest request;

    @BeforeEach
    void setUp() {
        usuario = UsuarioFactory.criar(2L, "Maria", "maria@example.com");
        amizade = AmizadeFactory.criar();
        request = AtualizarStatusAmizadeRequestFactory.criarParaAceitar();
    }

    @Test
    @DisplayName("Deve atualizar status de amizade com sucesso para ACEITA")
    void deveAtualizarStatusParaAceitaComSucesso() {
        // Arrange
        when(amizadeService.porId(request.getId())).thenReturn(amizade);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(amizadeRepository.save(any(Amizade.class))).thenReturn(amizade);

        // Act
        AmizadeResponse resultado = atualizarStatusAmizadeService.atualizarStatus(request);

        // Assert
        assertNotNull(resultado);
        assertEquals(amizade.getId(), resultado.getId());
        verify(amizadeService, times(1)).porId(request.getId());
        verify(usuarioAutenticadoService, times(1)).get();
        verify(atualizarStatusValidator, times(1)).validar(amizade, usuario.getId(), request.getStatus());
        verify(amizadeRepository, times(1)).save(amizade);
    }

    @Test
    @DisplayName("Deve atualizar status de amizade com sucesso para RECUSADA")
    void deveAtualizarStatusParaRecusadaComSucesso() {
        // Arrange
        request = AtualizarStatusAmizadeRequestFactory.criarParaRecusar();
        when(amizadeService.porId(request.getId())).thenReturn(amizade);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(amizadeRepository.save(any(Amizade.class))).thenReturn(amizade);

        // Act
        AmizadeResponse resultado = atualizarStatusAmizadeService.atualizarStatus(request);

        // Assert
        assertNotNull(resultado);
        assertEquals(amizade.getId(), resultado.getId());
        verify(amizadeService, times(1)).porId(request.getId());
        verify(usuarioAutenticadoService, times(1)).get();
        verify(atualizarStatusValidator, times(1)).validar(amizade, usuario.getId(), StatusAmizade.RECUSADA);
        verify(amizadeRepository, times(1)).save(amizade);
    }

    @Test
    @DisplayName("Deve lançar exceção quando validação falha")
    void deveLancarExcecaoQuandoValidacaoFalha() {
        // Arrange
        when(amizadeService.porId(request.getId())).thenReturn(amizade);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        doThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Erro de validação"))
                .when(atualizarStatusValidator).validar(amizade, usuario.getId(), request.getStatus());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            atualizarStatusAmizadeService.atualizarStatus(request);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        verify(amizadeRepository, never()).save(any());
    }
}


