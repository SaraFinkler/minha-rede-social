package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static br.com.cwi.redesocial.factory.PostFactory.getPost;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService tested;

    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("deve retornar post por id")
    void deveRetornarPostPorId() {

        Post post = getPost();

        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        Post response = tested.porId(1L);

        assertEquals(post.getId(), response.getId());
        assertEquals(post.getConteudo(), response.getConteudo());
    }

    @Test
    @DisplayName("deve lançar excecao quando post nao encontrado")
    void deveLancarExcecaoQuandoPostNaoEncontrado() {

        when(postRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tested.porId(1L)
        );

        assertEquals(
                "404 NOT_FOUND \"Post nao encontrado\"",
                exception.getMessage()
        );
    }
}