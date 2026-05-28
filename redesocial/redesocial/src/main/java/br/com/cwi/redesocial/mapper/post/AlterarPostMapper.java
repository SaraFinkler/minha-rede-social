package br.com.cwi.redesocial.mapper.post;

import br.com.cwi.redesocial.controller.request.post.AlterarPostRequest;
import br.com.cwi.redesocial.domain.Post;

public class AlterarPostMapper {
    public static void toEntity(Post post, AlterarPostRequest request) {
        post.setVisibilidade(request.getVisibilidade());
    }
}
