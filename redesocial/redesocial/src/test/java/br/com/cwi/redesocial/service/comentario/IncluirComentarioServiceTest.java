package br.com.cwi.redesocial.service.comentario;

import br.com.cwi.redesocial.controller.request.comentario.IncluirComentarioRequest;
import br.com.cwi.redesocial.controller.response.comentario.ComentarioResponse;
import br.com.cwi.redesocial.domain.Comentario;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.VisibilidadePost;
import br.com.cwi.redesocial.factory.ComentarioFactory;
import br.com.cwi.redesocial.factory.PostFactory;
import br.com.cwi.redesocial.factory.UsuarioFactory;
import br.com.cwi.redesocial.repository.ComentarioRepository;
import br.com.cwi.redesocial.service.amizade.SaoAmigosService;
import br.com.cwi.redesocial.service.post.PostService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para IncluirComentarioService")
class IncluirComentarioServiceTest {

    @Mock
    private PostService postService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private SaoAmigosService saoAmigosService;

    @InjectMocks
    private IncluirComentarioService incluirComentarioService;

    private Usuario usuarioAutenticado;
    private Usuario proprietarioPost;
    private Post postPublico;
    private Post postPrivado;
    private IncluirComentarioRequest request;

    @BeforeEach
    void setUp() {
        usuarioAutenticado = UsuarioFactory.getUsuario();
        usuarioAutenticado.setId(1L);
        usuarioAutenticado.setNomeCompleto("João");

        proprietarioPost = UsuarioFactory.getUsuario();
        proprietarioPost.setId(2L);
        proprietarioPost.setNomeCompleto("Maria");

        postPublico = PostFactory.getPost();
        postPublico.setVisibilidade(VisibilidadePost.PUBLICO);
        postPublico.setUsuario(proprietarioPost);

        postPrivado = PostFactory.getPost();
        postPrivado.setVisibilidade(VisibilidadePost.PRIVADO);
        postPrivado.setUsuario(proprietarioPost);

        request = ComentarioFactory.getIncluirComentarioRequest();
    }

    @Test
    @DisplayName("Deve criar comentário em post público")
    void deveCriarComentarioEmPostPublico() {
        // Arrange
        when(postService.porId(postPublico.getId())).thenReturn(postPublico);
        when(usuarioAutenticadoService.get()).thenReturn(usuarioAutenticado);
        when(comentarioRepository.save(any(Comentario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ComentarioResponse response = incluirComentarioService.incluir(postPublico.getId(), request);

        // Assert
        assertNotNull(response);
        assertEquals(request.getConteudo(), response.getConteudo());
        assertEquals(postPublico.getId(), response.getPostId());
        assertEquals(usuarioAutenticado.getId(), response.getUsuarioId());
        verify(postService, times(1)).porId(postPublico.getId());
        verify(usuarioAutenticadoService, times(1)).get();
        verify(comentarioRepository, times(1)).save(any(Comentario.class));
    }

    @Test
    @DisplayName("Deve criar comentário em post privado quando usuários são amigos")
    void deveCriarComentarioEmPostPrivadoQuandoSaoAmigos() {
        // Arrange
        when(postService.porId(postPrivado.getId())).thenReturn(postPrivado);
        when(usuarioAutenticadoService.get()).thenReturn(usuarioAutenticado);
        when(saoAmigosService.saoAmigos(usuarioAutenticado.getId(), proprietarioPost.getId()))
                .thenReturn(true);
        when(comentarioRepository.save(any(Comentario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ComentarioResponse response = incluirComentarioService.incluir(postPrivado.getId(), request);

        // Assert
        assertNotNull(response);
        assertEquals(request.getConteudo(), response.getConteudo());
        assertEquals(postPrivado.getId(), response.getPostId());
        verify(postService, times(1)).porId(postPrivado.getId());
        verify(usuarioAutenticadoService, times(1)).get();
        verify(saoAmigosService, times(1)).saoAmigos(usuarioAutenticado.getId(), proprietarioPost.getId());
        verify(comentarioRepository, times(1)).save(any(Comentario.class));
    }

    @Test
    @DisplayName("Deve lançar FORBIDDEN ao tentar comentar em post privado sem ser amigo")
    void deveLancarForbiddenAoComentarPostPrivadoSemSerAmigo() {
        // Arrange
        when(postService.porId(postPrivado.getId())).thenReturn(postPrivado);
        when(usuarioAutenticadoService.get()).thenReturn(usuarioAutenticado);
        when(saoAmigosService.saoAmigos(usuarioAutenticado.getId(), proprietarioPost.getId()))
                .thenReturn(false);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> incluirComentarioService.incluir(postPrivado.getId(), request));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        verify(postService, times(1)).porId(postPrivado.getId());
        verify(usuarioAutenticadoService, times(1)).get();
        verify(saoAmigosService, times(1)).saoAmigos(usuarioAutenticado.getId(), proprietarioPost.getId());
        verify(comentarioRepository, never()).save(any());
    }
}

