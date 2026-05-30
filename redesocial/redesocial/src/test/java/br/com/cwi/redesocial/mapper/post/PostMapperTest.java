package br.com.cwi.redesocial.mapper.post;

import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cwi.redesocial.factory.PostFactory.getPost;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostMapperTest {

    @Test
    @DisplayName("deve mapear entity para response")
    void deveMapearEntityParaResponse() {
        Post post = getPost();

        PostResponse response = PostMapper.toResponse(post);

        assertEquals(post.getId(), response.getId());
        assertEquals(post.getConteudo(), response.getConteudo());
        assertEquals(post.getVisibilidade(), response.getVisibilidade());
        assertEquals(post.getUsuario().getId(), response.getUsuario());
        assertEquals(post.getDataCriacao(), response.getDataCriacao());
    }
}
