package br.com.cwi.redesocial.validator.post;

import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.redesocial.factory.PostFactory.getPost;
import static br.com.cwi.redesocial.factory.UsuarioFactory.getUsuario;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioDonoPostValidatorTest {

    private final UsuarioDonoPostValidator tested =
            new UsuarioDonoPostValidator();

    @Test
    @DisplayName("deve validar usuario dono do post")
    void deveValidarUsuarioDonoDoPost() {

        Usuario usuario = getUsuario();

        Post post = getPost();
        post.setUsuario(usuario);

        assertDoesNotThrow(() ->
                tested.validar(usuario, post)
        );
    }

    @Test
    @DisplayName("deve lancar excecao quando usuario nao for dono do post")
    void deveLancarExcecaoQuandoUsuarioNaoForDonoDoPost() {

        Usuario usuario = getUsuario();

        Post post = getPost();

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tested.validar(usuario, post)
        );

        assertEquals(
                "403 FORBIDDEN \"Só o usuário dono do post pode alterar a visibilidade do post\"",
                exception.getMessage()
        );
    }
}
