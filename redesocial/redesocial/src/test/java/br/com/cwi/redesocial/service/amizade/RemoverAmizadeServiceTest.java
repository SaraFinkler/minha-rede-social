package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.StatusAmizade;
import br.com.cwi.redesocial.factory.AmizadeFactory;
import br.com.cwi.redesocial.factory.UsuarioFactory;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import br.com.cwi.redesocial.validator.amizade.PermiteRemoverValidator;
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
@DisplayName("Testes para RemoverAmizadeService")
class RemoverAmizadeServiceTest {

    @Mock
    private AmizadeRepository amizadeRepository;

    @Mock
    private AmizadeService amizadeService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private PermiteRemoverValidator permiteRemoverValidator;

    @InjectMocks
    private RemoverAmizadeService removerAmizadeService;

    private Usuario solicitante;
    private Usuario destinatario;
    private Amizade amizade;

    @BeforeEach
    void setUp() {
        solicitante = UsuarioFactory.getUsuario();
        solicitante.setId(1L);

        destinatario = UsuarioFactory.getUsuario();
        destinatario.setId(2L);

        amizade = AmizadeFactory.criar();
        amizade.setId(10L);
        amizade.setSolicitante(solicitante);
        amizade.setDestinatario(destinatario);
        amizade.setStatus(StatusAmizade.ACEITA);
    }

    @Test
    @DisplayName("Deve remover amizade quando usuário for o solicitante")
    void deveRemoverQuandoUserForSolicitante() {
        // Arrange
        when(amizadeService.porId(amizade.getId())).thenReturn(amizade);
        when(usuarioAutenticadoService.get()).thenReturn(solicitante);

        // Act
        removerAmizadeService.remover(amizade.getId());

        // Assert
        verify(amizadeService, times(1)).porId(amizade.getId());
        verify(usuarioAutenticadoService, times(1)).get();
        verify(permiteRemoverValidator, times(1)).validar(amizade, solicitante);
        verify(amizadeRepository, times(1)).delete(amizade);
    }

    @Test
    @DisplayName("Deve remover amizade quando usuário for o destinatário")
    void deveRemoverQuandoUserForDestinatario() {
        // Arrange
        when(amizadeService.porId(amizade.getId())).thenReturn(amizade);
        when(usuarioAutenticadoService.get()).thenReturn(destinatario);

        // Act
        removerAmizadeService.remover(amizade.getId());

        // Assert
        verify(amizadeService, times(1)).porId(amizade.getId());
        verify(usuarioAutenticadoService, times(1)).get();
        verify(permiteRemoverValidator, times(1)).validar(amizade, destinatario);
        verify(amizadeRepository, times(1)).delete(amizade);
    }

    @Test
    @DisplayName("Deve lançar FORBIDDEN quando usuário não tiver permissão")
    void deveLancarForbiddenQuandoSemPermissao() {
        // Arrange
        Usuario usuarioNaoAutorizado = UsuarioFactory.getUsuario();
        usuarioNaoAutorizado.setId(999L);

        when(amizadeService.porId(amizade.getId())).thenReturn(amizade);
        when(usuarioAutenticadoService.get()).thenReturn(usuarioNaoAutorizado);
        doThrow(new ResponseStatusException(HttpStatus.FORBIDDEN,
                "Você não tem permissão para remover esta amizade"))
                .when(permiteRemoverValidator).validar(amizade, usuarioNaoAutorizado);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> removerAmizadeService.remover(amizade.getId()));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        verify(amizadeRepository, never()).delete(any());
    }
}

