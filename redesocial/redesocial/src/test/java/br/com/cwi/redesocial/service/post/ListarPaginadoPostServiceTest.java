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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static br.com.cwi.redesocial.factory.PostFactory.getPost;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarPaginadoPostServiceTest {

    @InjectMocks
    private ListarPaginadoPostService tested;

    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("deve listar posts paginados")
    void deveListarPostsPaginados() {

        Post post = getPost();

        PageRequest pageable = PageRequest.of(0, 10);

        Page<Post> posts = new PageImpl<>(List.of(post));

        when(postRepository.findAll(pageable))
                .thenReturn(posts);

        Page<PostResponse> response = tested.listarPaginado(pageable);

        verify(postRepository).findAll(pageable);

        assertEquals(1, response.getContent().size());

        assertEquals(
                post.getConteudo(),
                response.getContent().get(0).getConteudo()
        );
    }
}
