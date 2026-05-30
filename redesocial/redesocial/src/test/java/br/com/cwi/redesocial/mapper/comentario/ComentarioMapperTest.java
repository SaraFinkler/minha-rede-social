package br.com.cwi.redesocial.mapper.comentario;

import br.com.cwi.redesocial.controller.request.comentario.IncluirComentarioRequest;
import br.com.cwi.redesocial.controller.response.comentario.ComentarioResponse;
import br.com.cwi.redesocial.domain.Comentario;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.factory.PostFactory;
import br.com.cwi.redesocial.factory.UsuarioFactory;
import br.com.cwi.redesocial.mapper.ComentarioMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cwi.redesocial.factory.ComentarioFactory.getComentario;
import static br.com.cwi.redesocial.factory.ComentarioFactory.getIncluirComentarioRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ComentarioMapperTest {

    @Test
    @DisplayName("deve mapear request para entity")
    void deveMapearRequestParaEntity() {
        IncluirComentarioRequest request = getIncluirComentarioRequest();
        Post post = PostFactory.getPost();
        Usuario usuario = UsuarioFactory.getUsuario();

        Comentario comentario = ComentarioMapper.toEntity(request, post, usuario);

        assertEquals(request.getConteudo(), comentario.getConteudo());
        assertSame(post, comentario.getPost());
        assertSame(usuario, comentario.getUsuario());
    }

    @Test
    @DisplayName("deve mapear entity para response")
    void deveMapearEntityParaResponse() {
        Comentario comentario = getComentario();

        ComentarioResponse response = ComentarioMapper.toResponse(comentario);

        assertEquals(comentario.getId(), response.getId());
        assertEquals(comentario.getConteudo(), response.getConteudo());
        assertEquals(comentario.getDataCriacao(), response.getDataCriacao());
        assertEquals(comentario.getPost().getId(), response.getPostId());
        assertEquals(comentario.getUsuario().getId(), response.getUsuarioId());
    }
}