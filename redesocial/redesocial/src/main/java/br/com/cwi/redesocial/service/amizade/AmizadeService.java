package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AmizadeService {

    @Autowired
    private AmizadeRepository amizadeRepository;

    public Amizade porId(long id) {
        return amizadeRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Amizade nao encontrado"));
    }
}