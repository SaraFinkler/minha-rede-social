package br.com.cwi.redesocial.factory;

import br.com.cwi.redesocial.controller.request.comentario.IncluirComentarioRequest;
import br.com.cwi.redesocial.domain.Comentario;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;

import java.time.LocalDateTime;

public class ComentarioFactory {
    public static IncluirComentarioRequest getIncluirComentarioRequest() {
        IncluirComentarioRequest request = new IncluirComentarioRequest();
        request.setConteudo("Comentario do post");

        return request;
    }

    public static Comentario getComentario() {
        return Comentario.builder()
                .id(3L)
                .conteudo("Comentario do post")
                .dataCriacao(LocalDateTime.of(2026, 5, 30, 10, 30))
                .post(Post.builder()
                        .id(1L)
                        .build())
                .usuario(Usuario.builder()
                        .id(2L)
                        .build())
                .build();
    }
}