package br.com.cwi.redesocial.mapper.amizade;

import br.com.cwi.redesocial.controller.response.amizade.AmizadeResponse;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.factory.AmizadeFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
class AmizadeMapperTest {

    @Test
    @DisplayName("deve instanciar mapper")
    void deveInstanciarMapper() {
        assertNotNull(new AmizadeMapper());
    }

    @Test
    @DisplayName("deve mapear entity para response")
    void deveMapearEntityParaResponse() {
        Amizade amizade = AmizadeFactory.criar();

        AmizadeResponse response = AmizadeMapper.toResponse(amizade);

        assertEquals(amizade.getId(), response.getId());
        assertEquals(amizade.getSolicitante().getId(), response.getSolicitanteId());
        assertEquals(amizade.getDestinatario().getId(), response.getDestinatarioId());
        assertEquals(amizade.getStatus(), response.getStatus());
    }
}
