package br.com.cwi.redesocial.service.comentario;

import br.com.cwi.redesocial.domain.Comentario;
import br.com.cwi.redesocial.factory.ComentarioFactory;
import br.com.cwi.redesocial.repository.ComentarioRepository;
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
@DisplayName("Testes para DeleteComentarioService")
class DeleteComentarioServiceTest {

    @Mock
    private ComentarioService comentarioService;

    @Mock
    private ComentarioRepository comentarioRepository;

    @InjectMocks
    private DeleteComentarioService deleteComentarioService;

    @Test
    @DisplayName("Deve deletar comentário existente")
    void deveDeletarComentarioExistente() {
        // Arrange
        Comentario comentario = ComentarioFactory.getComentario();
        when(comentarioService.porId(comentario.getId())).thenReturn(comentario);

        // Act
        deleteComentarioService.deletar(comentario.getId());

        // Assert
        verify(comentarioService, times(1)).porId(comentario.getId());
        verify(comentarioRepository, times(1)).delete(comentario);
    }

    @Test
    @DisplayName("Deve lançar NOT_FOUND ao tentar deletar comentário inexistente")
    void deveLancarNotFoundAoDeletarComentarioInexistente() {
        // Arrange
        long idInexistente = 999L;
        when(comentarioService.porId(idInexistente))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentário não encontrado"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> deleteComentarioService.deletar(idInexistente));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(comentarioService, times(1)).porId(idInexistente);
        verify(comentarioRepository, never()).delete(any());
    }
}

