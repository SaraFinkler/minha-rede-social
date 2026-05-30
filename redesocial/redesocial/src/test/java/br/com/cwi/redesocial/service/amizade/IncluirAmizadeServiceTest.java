package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.controller.request.amizade.IncluirAmizadeRequest;
import br.com.cwi.redesocial.controller.response.amizade.AmizadeResponse;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.StatusAmizade;
import br.com.cwi.redesocial.factory.AmizadeFactory;
import br.com.cwi.redesocial.factory.IncluirAmizadeRequestFactory;
import br.com.cwi.redesocial.factory.UsuarioFactory;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import br.com.cwi.redesocial.service.usuario.BuscarUsuarioService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import br.com.cwi.redesocial.validator.amizade.IncluirAmizadeValidator;
import br.com.cwi.redesocial.validator.amizade.SolicitacaoAmizadeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para IncluirAmizadeService")
class IncluirAmizadeServiceTest {

    @Mock
    private AmizadeRepository amizadeRepository;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private SolicitacaoAmizadeValidator solicitacaoAmizadeValidator;

    @Mock
    private IncluirAmizadeValidator incluirAmizadeValidator;

    @InjectMocks
    private IncluirAmizadeService incluirAmizadeService;

    private Usuario solicitante;
    private Usuario destinatario;
    private IncluirAmizadeRequest request;

    @BeforeEach
    void setUp() {
        solicitante = UsuarioFactory.criar(1L, "João", "joao@example.com");
        destinatario = UsuarioFactory.criar(2L, "Maria", "maria@example.com");
        request = IncluirAmizadeRequestFactory.criar(destinatario.getId());
    }

    @Test
    @DisplayName("Deve criar nova amizade quando não existir relacionamento anterior")
    void deveCriarNovaAmizadeQuandoNaoExistirRelacionamento() {
        // Arrange
        when(usuarioAutenticadoService.get()).thenReturn(solicitante);
        when(buscarUsuarioService.porId(request.getDestinatarioId())).thenReturn(destinatario);
        when(amizadeRepository.encontrarAmizade(solicitante.getId(), destinatario.getId()))
                .thenReturn(Optional.empty());
        when(amizadeRepository.save(any(Amizade.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        AmizadeResponse response = incluirAmizadeService.incluir(request);

        // Assert
        assertNotNull(response);
        assertEquals(solicitante.getId(), response.getSolicitanteId());
        assertEquals(destinatario.getId(), response.getDestinatarioId());
        assertEquals(StatusAmizade.PENDENTE, response.getStatus());
        verify(usuarioAutenticadoService, times(1)).get();
        verify(buscarUsuarioService, times(1)).porId(request.getDestinatarioId());
        verify(solicitacaoAmizadeValidator, times(1)).validar(solicitante.getId(), destinatario.getId());
        verify(amizadeRepository, times(1)).encontrarAmizade(solicitante.getId(), destinatario.getId());
        verify(amizadeRepository, times(1)).save(any(Amizade.class));
    }

    @Test
    @DisplayName("Deve reutilizar amizade recusada e voltar para PENDENTE")
    void deveReutilizarAmizadeRecusadaQuandoExistirRelacionamento() {
        // Arrange
        Amizade amizadeRecusada = AmizadeFactory.criar(solicitante, destinatario, StatusAmizade.RECUSADA);
        amizadeRecusada.setDataRespostaSolicitacao(java.time.LocalDateTime.now());

        when(usuarioAutenticadoService.get()).thenReturn(solicitante);
        when(buscarUsuarioService.porId(request.getDestinatarioId())).thenReturn(destinatario);
        when(amizadeRepository.encontrarAmizade(solicitante.getId(), destinatario.getId()))
                .thenReturn(Optional.of(amizadeRecusada));
        when(amizadeRepository.save(any(Amizade.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        AmizadeResponse response = incluirAmizadeService.incluir(request);

        // Assert
        assertNotNull(response);
        assertEquals(amizadeRecusada.getId(), response.getId());
        assertEquals(StatusAmizade.PENDENTE, response.getStatus());
        assertEquals(StatusAmizade.PENDENTE, amizadeRecusada.getStatus());
        assertNull(amizadeRecusada.getDataRespostaSolicitacao());
        verify(incluirAmizadeValidator, times(1)).validar(StatusAmizade.RECUSADA);
        verify(amizadeRepository, times(1)).save(amizadeRecusada);
    }

    @Test
    @DisplayName("Deve lançar BAD_REQUEST quando o usuário solicitar amizade com ele mesmo")
    void deveLancarBadRequestQuandoSolicitanteForODestinatario() {
        // Arrange
        request = IncluirAmizadeRequestFactory.criar(solicitante.getId());
        when(usuarioAutenticadoService.get()).thenReturn(solicitante);
        when(buscarUsuarioService.porId(request.getDestinatarioId())).thenReturn(solicitante);
        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "O usuário não pode solicitar amizade com ele mesmo"))
                .when(solicitacaoAmizadeValidator)
                .validar(solicitante.getId(), solicitante.getId());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> incluirAmizadeService.incluir(request));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(usuarioAutenticadoService, times(1)).get();
        verify(buscarUsuarioService, times(1)).porId(request.getDestinatarioId());
        verify(amizadeRepository, never()).encontrarAmizade(anyLong(), anyLong());
        verify(amizadeRepository, never()).save(any());
    }
}

