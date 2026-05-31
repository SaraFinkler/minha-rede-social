package br.com.cwi.redesocial.mapper.post;

import br.com.cwi.redesocial.controller.request.post.AlterarPostRequest;
import br.com.cwi.redesocial.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cwi.redesocial.factory.PostFactory.getAlterarPostRequest;
import static br.com.cwi.redesocial.factory.PostFactory.getPost;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AlterarPostMapperTest {

    @Test
    @DisplayName("deve instanciar mapper")
    void deveInstanciarMapper() {
        assertNotNull(new AlterarPostMapper());
    }

    @Test
    @DisplayName("deve mapear request para entity")
    void deveMapearRequestParaEntity() {
        Post post = getPost();
        AlterarPostRequest request = getAlterarPostRequest();

        AlterarPostMapper.toEntity(post, request);

        assertEquals(request.getVisibilidade(), post.getVisibilidade());
    }
}
