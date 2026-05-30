package br.com.cwi.redesocial.service.curtida;

import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.VisibilidadePost;
import br.com.cwi.redesocial.repository.CurtidaRepository;
import br.com.cwi.redesocial.service.amizade.SaoAmigosService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import static br.com.cwi.redesocial.factory.PostFactory.getPost;
import static br.com.cwi.redesocial.factory.UsuarioFactory.getUsuario;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurtidaServiceTest {

    @InjectMocks
    private CurtidaService tested;

    @Mock
    private SaoAmigosService saoAmigosService;

    @Mock
    private CurtidaRepository curtidaRepository;

    @Test
    @DisplayName("deve validar quando usuario ainda nao curtiu")
    void deveValidarQuandoUsuarioAindaNaoCurtiu() {

        Usuario usuario = getUsuario();
        Post post = getPost();

        when(curtidaRepository.existsByUsuarioIdAndPostId(
                usuario.getId(),
                post.getId()
        )).thenReturn(false);

        assertDoesNotThrow(() ->
                tested.validarJaCurtiu(usuario, post)
        );
    }

    @Test
    @DisplayName("deve lançar excecao quando usuario ja curtiu")
    void deveLancarExcecaoQuandoUsuarioJaCurtiu() {

        Usuario usuario = getUsuario();
        Post post = getPost();

        when(curtidaRepository.existsByUsuarioIdAndPostId(
                usuario.getId(),
                post.getId()
        )).thenReturn(true);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tested.validarJaCurtiu(usuario, post)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals(
                "409 CONFLICT \"Você já curtiu este post\"",
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("deve validar quando usuario ja curtiu")
    void deveValidarQuandoUsuarioJaCurtiu() {

        Usuario usuario = getUsuario();
        Post post = getPost();

        when(curtidaRepository.existsByUsuarioIdAndPostId(
                usuario.getId(),
                post.getId()
        )).thenReturn(true);

        assertDoesNotThrow(() ->
                tested.validarNaoCurtiu(usuario, post)
        );
    }

    @Test
    @DisplayName("deve lançar excecao quando usuario nao curtiu")
    void deveLancarExcecaoQuandoUsuarioNaoCurtiu() {

        Usuario usuario = getUsuario();
        Post post = getPost();

        when(curtidaRepository.existsByUsuarioIdAndPostId(
                usuario.getId(),
                post.getId()
        )).thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tested.validarNaoCurtiu(usuario, post)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals(
                "409 CONFLICT \"Você ainda não curtiu este post\"",
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("deve validar quando post for publico")
    void deveValidarQuandoPostForPublico() {

        Usuario usuario = getUsuario();
        Post post = getPost();

        post.setVisibilidade(VisibilidadePost.PUBLICO);

        assertDoesNotThrow(() ->
                tested.validarSeEhAmigo(usuario, post)
        );

        verifyNoInteractions(saoAmigosService);
    }

    @Test
    @DisplayName("deve validar quando usuarios forem amigos")
    void deveValidarQuandoUsuariosForemAmigos() {

        Usuario usuario = getUsuario();
        Post post = getPost();

        post.setVisibilidade(VisibilidadePost.PRIVADO);

        when(saoAmigosService.saoAmigos(
                usuario.getId(),
                post.getUsuario().getId()
        )).thenReturn(true);

        assertDoesNotThrow(() ->
                tested.validarSeEhAmigo(usuario, post)
        );
    }

    @Test
    @DisplayName("deve lançar excecao quando usuarios nao forem amigos")
    void deveLancarExcecaoQuandoUsuariosNaoForemAmigos() {

        Usuario usuario = getUsuario();
        Post post = getPost();

        post.setVisibilidade(VisibilidadePost.PRIVADO);

        when(saoAmigosService.saoAmigos(
                usuario.getId(),
                post.getUsuario().getId()
        )).thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tested.validarSeEhAmigo(usuario, post)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals(
                "409 CONFLICT \"Você precisa ser amigo deste usuário para interagir com ele\"",
                exception.getMessage()
        );
    }
}