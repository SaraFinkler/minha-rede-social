package br.com.cwi.redesocial.validator.amizade;

import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.enums.StatusAmizade;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AtualizarStatusValidator {
    public void validar(Amizade amizade, long usuarioId, StatusAmizade statusAmizade) {
        if (amizade.getSolicitante().getId().equals(usuarioId) && statusAmizade != StatusAmizade.RECUSADA) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Usuário solicitante não pode atualizar status da amizade");
        }

        if(amizade.getStatus() == statusAmizade || statusAmizade == StatusAmizade.PENDENTE) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Status da amizade não pode ser alterado para o mesmo status ou para pendente");
        }
    }
}