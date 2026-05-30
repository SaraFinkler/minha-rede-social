package br.com.cwi.redesocial.service.curtida;

import br.com.cwi.redesocial.controller.request.curtida.CurtidaRequest;
import br.com.cwi.redesocial.domain.Curtida;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.CurtidaRepository;
import br.com.cwi.redesocial.service.amizade.SaoAmigosService;
import br.com.cwi.redesocial.service.post.PostService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static br.com.cwi.redesocial.factory.PostFactory.getPost;
import static br.com.cwi.redesocial.factory.UsuarioFactory.getUsuario;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoverCurtidaServiceTest {

    @InjectMocks
    private RemoverCurtidaService tested;

    @Mock
    private PostService postService;

    @Mock
    private CurtidaService curtidaService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private SaoAmigosService saoAmigosService;

    @Mock
    private CurtidaRepository curtidaRepository;

    @Test
    @DisplayName("deve remover curtida")
    void deveRemoverCurtida() throws Exception {

        CurtidaRequest request = new CurtidaRequest();

        Field field = CurtidaRequest.class.getDeclaredField("postId");
        field.setAccessible(true);
        field.set(request, 1L);

        Post post = getPost();
        Usuario usuario = getUsuario();

        Curtida curtida = Curtida.builder()
                .id(1L)
                .post(post)
                .usuario(usuario)
                .build();

        when(postService.porId(1L))
                .thenReturn(post);

        when(usuarioAutenticadoService.get())
                .thenReturn(usuario);

        when(curtidaRepository.findByUsuarioIdAndPostId(
                usuario.getId(),
                request.getPostId()
        )).thenReturn(Optional.of(curtida));

        assertDoesNotThrow(() ->
                tested.deletar(request)
        );

        verify(curtidaService)
                .validarSeEhAmigo(usuario, post);

        verify(curtidaService)
                .validarNaoCurtiu(usuario, post);

        verify(curtidaRepository)
                .delete(curtida);
    }
}