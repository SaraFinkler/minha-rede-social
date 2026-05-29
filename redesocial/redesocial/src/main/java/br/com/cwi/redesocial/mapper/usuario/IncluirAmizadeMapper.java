package br.com.cwi.redesocial.mapper.usuario;

import br.com.cwi.redesocial.controller.request.amizade.IncluirAmizadeRequest;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.enums.StatusAmizade;

public class IncluirAmizadeMapper {
    public static Amizade toEntity(IncluirAmizadeRequest request) {
        return Amizade.builder()
                .status(StatusAmizade.PENDENTE)
                .build();
    }
}
