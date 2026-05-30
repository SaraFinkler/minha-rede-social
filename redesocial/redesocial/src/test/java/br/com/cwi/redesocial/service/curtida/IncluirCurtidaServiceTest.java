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
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.redesocial.factory.PostFactory.getPost;
import static br.com.cwi.redesocial.factory.UsuarioFactory.getUsuario;
import static org.mockito.Mockito.*;
import static br.com.cwi.redesocial.mapper.curtida.CurtidaMapper.toEntity;

@ExtendWith(MockitoExtension.class)
class IncluirCurtidaServiceTest {

    @InjectMocks
    private IncluirCurtidaService tested;

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
    @DisplayName("deve incluir curtida")
    void deveIncluirCurtida() {

        CurtidaRequest request = new CurtidaRequest();
        request.setPostId(1L);

        Post post = getPost();
        Usuario usuario = getUsuario();

        Curtida curtida = Curtida.builder()
                .post(post)
                .usuario(usuario)
                .build();

        when(postService.porId(request.getPostId()))
                .thenReturn(post);

        when(usuarioAutenticadoService.get())
                .thenReturn(usuario);

        try (MockedStatic<br.com.cwi.redesocial.mapper.curtida.CurtidaMapper> mapper =
                     mockStatic(br.com.cwi.redesocial.mapper.curtida.CurtidaMapper.class)) {

            mapper.when(() -> toEntity(post, usuario))
                    .thenReturn(curtida);

            tested.incluir(request);

            verify(curtidaService)
                    .validarSeEhAmigo(usuario, post);

            verify(curtidaService)
                    .validarJaCurtiu(usuario, post);

            verify(curtidaRepository)
                    .save(curtida);
        }
    }
}