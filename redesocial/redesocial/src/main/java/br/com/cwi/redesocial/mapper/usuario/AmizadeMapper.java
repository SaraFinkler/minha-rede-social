package br.com.cwi.redesocial.mapper.usuario;

import br.com.cwi.redesocial.controller.response.AmizadeResponse;
import br.com.cwi.redesocial.domain.Amizade;

public class AmizadeMapper {
    public static AmizadeResponse toResponse(Amizade amizade) {
        return AmizadeResponse.builder()
                .id(amizade.getId())
                .solicitanteId(amizade.getSolicitante().getId())
                .destinatarioId(amizade.getDestinatario().getId())
                .status(amizade.getStatus())
                .build();
    }
}
