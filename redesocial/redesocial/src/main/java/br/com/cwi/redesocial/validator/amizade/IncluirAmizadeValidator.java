package br.com.cwi.redesocial.validator.amizade;

import br.com.cwi.redesocial.enums.StatusAmizade;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class IncluirAmizadeValidator {
    public void validar(StatusAmizade statusAmizade) {
        if (statusAmizade != StatusAmizade.RECUSADA) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe amizade ou solicitação entre esses usuários"
            );
        }
    }
}