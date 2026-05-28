package br.com.cwi.redesocial.mapper;

import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.domain.Post;

public class PostMapper {
    public static PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .conteudo(post.getConteudo())
                .visibilidade(post.getVisibilidade())
                .ativo(post.getAtivo())
                .dataCriacao(post.getDataCriacao())
                .dataAlteracao(post.getDataAlteracao())
                .build();
    }
}
