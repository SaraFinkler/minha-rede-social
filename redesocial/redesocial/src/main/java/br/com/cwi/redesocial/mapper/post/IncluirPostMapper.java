package br.com.cwi.redesocial.mapper.post;

import br.com.cwi.redesocial.controller.request.post.IncluirPostRequest;
import br.com.cwi.redesocial.domain.Post;

public class IncluirPostMapper {
    public static Post toEntity(IncluirPostRequest request) {
        return Post.builder()
                .conteudo(request.getConteudo())
                .visibilidade(request.getVisibilidade())
                .build();
    }
}
