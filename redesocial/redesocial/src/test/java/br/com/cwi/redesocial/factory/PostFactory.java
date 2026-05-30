package br.com.cwi.redesocial.factory;

import br.com.cwi.redesocial.controller.request.post.AlterarPostRequest;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.enums.VisibilidadePost;

import java.time.LocalDateTime;

public class PostFactory {

    public static Post getPost() {
        return Post.builder()
                .id(1L)
                .conteudo("Conteudo do post")
                .visibilidade(VisibilidadePost.PUBLICO)
                .dataCriacao(LocalDateTime.of(2026, 5, 30, 10, 30))
                .usuario(UsuarioFactory.getUsuario())
                .build();
    }

    public static AlterarPostRequest getAlterarPostRequest() {
        AlterarPostRequest request = new AlterarPostRequest();
        request.setVisibilidade(VisibilidadePost.PRIVADO);

        return request;
    }
}
