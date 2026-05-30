package br.com.cwi.redesocial.service.post;


import br.com.cwi.redesocial.controller.request.post.IncluirPostRequest;
import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.PostRepository;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static br.com.cwi.redesocial.factory.UsuarioFactory.getUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncluirPostServiceTest {

    @InjectMocks
    private IncluirPostService tested;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("deve incluir post")
    void deveIncluirPost() throws Exception {

        IncluirPostRequest request = new IncluirPostRequest();

        Field conteudo = IncluirPostRequest.class
                .getDeclaredField("conteudo");

        conteudo.setAccessible(true);
        conteudo.set(request, "conteudo do post");

        Field visibilidade = IncluirPostRequest.class
                .getDeclaredField("visibilidade");

        visibilidade.setAccessible(true);
        visibilidade.set(request,
                br.com.cwi.redesocial.enums.VisibilidadePost.PUBLICO);

        Usuario usuario = getUsuario();

        when(usuarioAutenticadoService.get())
                .thenReturn(usuario);

        PostResponse response = tested.incluir(request);

        verify(postRepository).save(any(Post.class));

        assertEquals("conteudo do post", response.getConteudo());

        assertEquals(
                br.com.cwi.redesocial.enums.VisibilidadePost.PUBLICO,
                response.getVisibilidade()
        );
    }
}