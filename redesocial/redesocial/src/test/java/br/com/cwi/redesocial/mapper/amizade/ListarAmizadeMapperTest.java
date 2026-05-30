package br.com.cwi.redesocial.mapper.amizade;

import br.com.cwi.redesocial.controller.response.amizade.ListarAmizadeResponse;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.factory.AmizadeFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ListarAmizadeMapperTest {

    @Test
    @DisplayName("deve instanciar mapper")
    void deveInstanciarMapper() {
        assertNotNull(new ListarAmizadeMapper());
    }

    @Test
    @DisplayName("deve mapear response com destinatario quando usuario autenticado for solicitante")
    void deveMapearResponseComDestinatarioQuandoUsuarioAutenticadoForSolicitante() {
        Amizade amizade = AmizadeFactory.getAmizade();

        ListarAmizadeResponse response =
                ListarAmizadeMapper.toResponse(amizade, 1L);

        assertEquals(amizade.getId(), response.getId());
        assertEquals(
                amizade.getDestinatario().getNomeCompleto(),
                response.getNomeAmigo()
        );
        assertEquals(
                amizade.getDestinatario().getEmail(),
                response.getEmailAmigo()
        );
    }

    @Test
    @DisplayName("deve mapear response com solicitante quando usuario autenticado for destinatario")
    void deveMapearResponseComSolicitanteQuandoUsuarioAutenticadoForDestinatario() {
        Amizade amizade = AmizadeFactory.getAmizade();

        ListarAmizadeResponse response =
                ListarAmizadeMapper.toResponse(amizade, 2L);

        assertEquals(amizade.getId(), response.getId());
        assertEquals(
                amizade.getSolicitante().getNomeCompleto(),
                response.getNomeAmigo()
        );
        assertEquals(
                amizade.getSolicitante().getEmail(),
                response.getEmailAmigo()
        );
    }
}
