package br.com.cwi.redesocial.mapper.post;

import br.com.cwi.redesocial.controller.response.post.PostPerfilResponse;
import br.com.cwi.redesocial.domain.Post;

public class PostPerfilMapper {
    public static PostPerfilResponse toResponse(Post post) {
        return PostPerfilResponse.builder()
                .id(post.getId())
                .conteudo(post.getConteudo())
                .visibilidade(post.getVisibilidade())
                .ativo(post.getAtivo())
                .dataCriacao(post.getDataCriacao())
                .dataAlteracao(post.getDataAlteracao())
                .usuarioId(post.getUsuario().getId())
                .nomeAutor(post.getUsuario().getNomeCompleto())
                .apelidoAutor(post.getUsuario().getApelido())
                .build();
    }
}
