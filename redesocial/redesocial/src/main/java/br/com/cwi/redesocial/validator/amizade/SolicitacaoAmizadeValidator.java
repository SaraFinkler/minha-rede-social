package br.com.cwi.redesocial.validator.amizade;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class SolicitacaoAmizadeValidator {
    public void validar(long solicitanteId, long destinatarioId) {
        if (solicitanteId == destinatarioId) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O usuário não pode solicitar amizade com ele mesmo");
        }
    }
}