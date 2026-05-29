package br.com.cwi.redesocial.mapper.amizade;

import br.com.cwi.redesocial.controller.response.amizade.ListarAmizadeResponse;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;

public class ListarAmizadeMapper {
    public static ListarAmizadeResponse toResponse(Amizade amizade, Long usuarioAutenticadoId) {
        Usuario amigo;
        if (amizade.getSolicitante().getId().equals(usuarioAutenticadoId)) {
            amigo = amizade.getDestinatario();
        } else {
            amigo = amizade.getSolicitante();
        }

        return ListarAmizadeResponse.builder()
                .id(amizade.getId())
                .nomeAmigo(amigo.getNomeCompleto())
                .emailAmigo(amigo.getEmail())
                .build();
    }
}
