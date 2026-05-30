package br.com.cwi.redesocial.mapper.post;

import br.com.cwi.redesocial.controller.response.post.PostPerfilResponse;
import br.com.cwi.redesocial.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cwi.redesocial.factory.PostFactory.getPost;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PostPerfilMapperTest {

    @Test
    @DisplayName("deve instanciar mapper")
    void deveInstanciarMapper() {
        assertNotNull(new PostPerfilMapper());
    }

    @Test
    @DisplayName("deve mapear entity para response")
    void deveMapearEntityParaResponse() {
        Post post = getPost();

        PostPerfilResponse response = PostPerfilMapper.toResponse(post);

        assertEquals(post.getId(), response.getId());
        assertEquals(post.getConteudo(), response.getConteudo());
        assertEquals(post.getVisibilidade(), response.getVisibilidade());
        assertEquals(post.getDataCriacao(), response.getDataCriacao());

        assertEquals(post.getUsuario().getId(), response.getUsuarioId());
        assertEquals(post.getUsuario().getNomeCompleto(), response.getNomeAutor());
        assertEquals(post.getUsuario().getApelido(), response.getApelidoAutor());
    }
}
