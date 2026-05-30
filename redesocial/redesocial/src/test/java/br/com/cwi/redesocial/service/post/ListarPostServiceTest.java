package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static br.com.cwi.redesocial.factory.PostFactory.getPost;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarPostServiceTest {

    @InjectMocks
    private ListarPostService tested;

    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("deve listar posts")
    void deveListarPosts() {

        Post post = getPost();

        when(postRepository.findAll())
                .thenReturn(List.of(post));

        List<PostResponse> response = tested.listar();

        verify(postRepository).findAll();

        assertEquals(1, response.size());

        assertEquals(
                post.getConteudo(),
                response.get(0).getConteudo()
        );

        assertEquals(
                post.getVisibilidade(),
                response.get(0).getVisibilidade()
        );
    }
}
