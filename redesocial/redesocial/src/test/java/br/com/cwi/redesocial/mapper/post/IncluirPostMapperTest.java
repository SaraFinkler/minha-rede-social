package br.com.cwi.redesocial.mapper.post;

import br.com.cwi.redesocial.controller.request.post.IncluirPostRequest;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.enums.VisibilidadePost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IncluirPostMapperTest {

    @Test
    @DisplayName("deve instanciar mapper")
    void deveInstanciarMapper() {
        assertNotNull(new IncluirPostMapper());
    }

    @Test
    @DisplayName("deve mapear request para entity")
    void deveMapearRequestParaEntity() {
        IncluirPostRequest request = getIncluirPostRequest();

        Post post = IncluirPostMapper.toEntity(request);

        assertEquals(request.getConteudo(), post.getConteudo());
        assertEquals(request.getVisibilidade(), post.getVisibilidade());
    }

    private IncluirPostRequest getIncluirPostRequest() {
        IncluirPostRequest request = new IncluirPostRequest();

        request.setConteudo("Meu primeiro post");
        request.setVisibilidade(VisibilidadePost.PUBLICO);

        return request;
    }
}
