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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para ComentarioService")
class ComentarioServiceTest {

    @Mock
    private ComentarioRepository comentarioRepository;

    @InjectMocks
    private ComentarioService comentarioService;

    @Test
    @DisplayName("Deve buscar comentário existente por ID")
    void deveBuscarComentarioExistente() {
        // Arrange
        Comentario comentario = ComentarioFactory.getComentario();
        when(comentarioRepository.findById(comentario.getId()))
                .thenReturn(Optional.of(comentario));

        // Act
        Comentario resultado = comentarioService.porId(comentario.getId());

        // Assert
        assertNotNull(resultado);
        assertEquals(comentario.getId(), resultado.getId());
        assertEquals(comentario.getConteudo(), resultado.getConteudo());
        verify(comentarioRepository, times(1)).findById(comentario.getId());
    }

    @Test
    @DisplayName("Deve lançar NOT_FOUND quando comentário não existe")
    void deveLancarNotFoundQuandoComentarioNaoExiste() {
        // Arrange
        long idInexistente = 999L;
        when(comentarioRepository.findById(idInexistente))
                .thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> comentarioService.porId(idInexistente));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(comentarioRepository, times(1)).findById(idInexistente);
    }
}

