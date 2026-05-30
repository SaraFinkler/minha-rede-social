package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.controller.request.post.AlterarPostRequest;
import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.VisibilidadePost;
import br.com.cwi.redesocial.repository.PostRepository;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import br.com.cwi.redesocial.validator.post.UsuarioDonoPostValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.redesocial.factory.PostFactory.getPost;
import static br.com.cwi.redesocial.factory.UsuarioFactory.getUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlterarPostServiceTest {

    @InjectMocks
    private AlterarPostService tested;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostService postService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private UsuarioDonoPostValidator usuarioDonoPostValidator;

    @Test
    @DisplayName("deve alterar post")
    void deveAlterarPost() {

        long id = 1L;

        AlterarPostRequest request = new AlterarPostRequest();
        request.setVisibilidade(VisibilidadePost.PRIVADO);

        Usuario usuario = getUsuario();

        Post post = getPost();

        when(postService.porId(id))
                .thenReturn(post);

        when(usuarioAutenticadoService.get())
                .thenReturn(usuario);

        when(postRepository.save(post))
                .thenReturn(post);

        PostResponse response = tested.alterar(id, request);

        verify(usuarioDonoPostValidator)
                .validar(usuario, post);

        verify(postRepository)
                .save(post);

        assertEquals(VisibilidadePost.PRIVADO, post.getVisibilidade());

        assertNotNull(response);
        assertNotNull(response.getId());
    }
}
