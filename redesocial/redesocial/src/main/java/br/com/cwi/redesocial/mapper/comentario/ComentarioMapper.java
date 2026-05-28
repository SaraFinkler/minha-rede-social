package br.com.cwi.redesocial.mapper.comentario;

import br.com.cwi.redesocial.controller.request.comentario.IncluirComentarioRequest;
import br.com.cwi.redesocial.controller.request.usuario.UsuarioRequest;
import br.com.cwi.redesocial.controller.response.comentario.ComentarioResponse;
import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Comentario;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;

public class ComentarioMapper {
    public static Comentario toEntity(IncluirComentarioRequest request, Post post, Usuario usuario) {
        return Comentario.builder()
                .conteudo(request.getConteudo())
                .post(post)
                .usuario(usuario)
                .build();
    }

    public static ComentarioResponse toResponse(Comentario comentario) {
        return ComentarioResponse.builder()
                .id(comentario.getId())
                .conteudo(comentario.getConteudo())
                .dataCriacao(comentario.getDataCriacao())
                .postId(comentario.getPost().getId())
                .usuarioId(comentario.getUsuario().getId())
                .build();
    }
}
