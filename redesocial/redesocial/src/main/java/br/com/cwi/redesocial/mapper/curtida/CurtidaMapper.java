package br.com.cwi.redesocial.mapper.curtida;

import br.com.cwi.redesocial.domain.Curtida;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;

public class CurtidaMapper {
    public static Curtida toEntity(Post post, Usuario usuario) {
        return Curtida.builder()
                .post(post)
                .usuario(usuario)
                .build();
    }
}
