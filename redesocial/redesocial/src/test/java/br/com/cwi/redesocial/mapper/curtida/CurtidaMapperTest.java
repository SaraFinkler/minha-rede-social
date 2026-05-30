package br.com.cwi.redesocial.mapper.curtida;

import br.com.cwi.redesocial.domain.Curtida;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.factory.UsuarioFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cwi.redesocial.factory.CurtidaFactory.getPost;
import static org.junit.jupiter.api.Assertions.assertSame;

public class CurtidaMapperTest {

    @Test
    @DisplayName("deve mapear post e usuario para entity")
    void deveMapearPostEUsuarioParaEntity() {
        Post post = getPost();
        Usuario usuario = UsuarioFactory.getUsuario();

        Curtida curtida = CurtidaMapper.toEntity(post, usuario);

        assertSame(post, curtida.getPost());
        assertSame(usuario, curtida.getUsuario());
    }
}
