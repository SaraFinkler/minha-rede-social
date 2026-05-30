package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoverPostServiceTest {

    @InjectMocks
    private RemoverPostService tested;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostService postService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private UsuarioDonoPostValidator usuarioDonoPostValidator;

    @Test
    @DisplayName("deve remover post")
    void deveRemoverPost() {

        long id = 1L;

        Post post = getPost();

        Usuario usuario = getUsuario();

        when(postService.porId(id))
                .thenReturn(post);

        when(usuarioAutenticadoService.get())
                .thenReturn(usuario);

        tested.remover(id);

        verify(usuarioDonoPostValidator)
                .validar(usuario, post);

        verify(postRepository)
                .delete(post);
    }
}
