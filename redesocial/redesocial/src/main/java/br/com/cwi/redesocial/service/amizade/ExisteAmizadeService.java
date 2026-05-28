package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.repository.AmizadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ExisteAmizadeService {

    @Autowired
    private AmizadeRepository amizadeRepository;

    public void existeAmizade(long solicitanteId, long destinatarioId) {
          boolean jaExiste = amizadeRepository.existsBetweenUsers(
                solicitanteId,
                destinatarioId);

        if (jaExiste) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Já existe amizade ou solicitação entre esses usuários");
        }
    }
}