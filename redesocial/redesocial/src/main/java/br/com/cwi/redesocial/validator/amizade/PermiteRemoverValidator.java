package br.com.cwi.redesocial.validator.amizade;

import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class PermiteRemoverValidator {
    public void validar(Amizade amizade, Usuario usuario) {
        boolean permitidoRemover =
                amizade.getSolicitante().getId().equals(usuario.getId()) ||
                        amizade.getDestinatario().getId().equals(usuario.getId());

        if (!permitidoRemover) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você não tem permissão para remover esta amizade"
            );
        }
    }
}