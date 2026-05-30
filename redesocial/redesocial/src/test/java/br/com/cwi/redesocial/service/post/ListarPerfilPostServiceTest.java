package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.controller.response.post.PostPerfilResponse;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.PostRepository;
import br.com.cwi.redesocial.service.usuario.BuscarUsuarioService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
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
import static br.com.cwi.redesocial.factory.UsuarioFactory.getUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarPerfilPostServiceTest {

    @InjectMocks
    private ListarPerfilPostService tested;

    @Mock
    private PostRepository postRepository;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("deve listar posts do perfil")
    void deveListarPostsDoPerfil() {

        Long pessoaId = 2L;

        Usuario usuario = getUsuario();

        Post post = getPost();

        PageRequest pageable = PageRequest.of(0, 10);

        Page<Post> posts = new PageImpl<>(List.of(post));

        when(usuarioAutenticadoService.get())
                .thenReturn(usuario);

        when(postRepository.buscarPerfil(
                usuario.getId(),
                pessoaId,
                pageable
        )).thenReturn(posts);

        Page<PostPerfilResponse> response =
                tested.listarPerfil(pessoaId, pageable);

        verify(buscarUsuarioService).porId(pessoaId);

        verify(postRepository).buscarPerfil(
                usuario.getId(),
                pessoaId,
                pageable
        );

        assertEquals(1, response.getContent().size());

        assertEquals(
                post.getConteudo(),
                response.getContent().get(0).getConteudo()
        );
    }

    @Test
    @DisplayName("deve listar perfil quando pessoaId for nulo")
    void deveListarPerfilQuandoPessoaIdForNulo() {

        Usuario usuario = getUsuario();

        Post post = getPost();

        PageRequest pageable = PageRequest.of(0, 10);

        Page<Post> posts = new PageImpl<>(List.of(post));

        when(usuarioAutenticadoService.get())
                .thenReturn(usuario);

        when(postRepository.buscarPerfil(
                usuario.getId(),
                null,
                pageable
        )).thenReturn(posts);

        Page<PostPerfilResponse> response =
                tested.listarPerfil(null, pageable);

        verify(buscarUsuarioService, never()).porId(anyLong());

        verify(postRepository).buscarPerfil(
                usuario.getId(),
                null,
                pageable
        );

        assertEquals(1, response.getContent().size());
    }
}
