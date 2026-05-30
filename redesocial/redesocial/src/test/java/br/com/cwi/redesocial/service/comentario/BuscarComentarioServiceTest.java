package br.com.cwi.redesocial.service.comentario;

import br.com.cwi.redesocial.controller.response.comentario.ComentarioResponse;
import br.com.cwi.redesocial.domain.Comentario;
import br.com.cwi.redesocial.factory.ComentarioFactory;
import br.com.cwi.redesocial.mapper.comentario.ComentarioMapper;
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
@DisplayName("Testes para BuscarComentarioService")
class BuscarComentarioServiceTest {

    @Mock
    private ComentarioService comentarioService;

    @InjectMocks
    private BuscarComentarioService buscarComentarioService;

    @Test
    @DisplayName("Deve buscar comentário e retornar response")
    void deveBuscarComentarioERetornarResponse() {
        // Arrange
        Comentario comentario = ComentarioFactory.getComentario();
        when(comentarioService.porId(comentario.getId()))
                .thenReturn(comentario);

        // Act
        ComentarioResponse resultado = buscarComentarioService.buscarComentario(comentario.getId());

        // Assert
        assertNotNull(resultado);
        assertEquals(comentario.getId(), resultado.getId());
        assertEquals(comentario.getConteudo(), resultado.getConteudo());
        verify(comentarioService, times(1)).porId(comentario.getId());
    }

    @Test
    @DisplayName("Deve lançar NOT_FOUND quando comentário não existe")
    void deveLancarNotFoundQuandoComentarioNaoExiste() {
        // Arrange
        long idInexistente = 999L;
        when(comentarioService.porId(idInexistente))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentário não encontrado"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> buscarComentarioService.buscarComentario(idInexistente));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(comentarioService, times(1)).porId(idInexistente);
    }
}

