package br.com.cwi.redesocial.factory;

import br.com.cwi.redesocial.domain.Curtida;

public class CurtidaFactory {

    public static Curtida getCurtida() {
        return Curtida.builder()
                .id(1L)
                .post(PostFactory.getPost())
                .usuario(UsuarioFactory.getUsuario())
                .build();
    }
}
