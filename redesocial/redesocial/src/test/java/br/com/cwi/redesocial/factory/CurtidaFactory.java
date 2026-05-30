package br.com.cwi.redesocial.factory;

import br.com.cwi.redesocial.domain.Post;

public class CurtidaFactory {

    public static Post getPost() {
        return Post.builder()
                .id(1L)
                .build();
    }
}
