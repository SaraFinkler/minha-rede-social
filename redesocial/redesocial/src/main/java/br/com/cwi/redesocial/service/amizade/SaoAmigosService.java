package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.repository.AmizadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaoAmigosService {
    @Autowired
    AmizadeRepository amizadeRepository;

    public boolean saoAmigos(long solicitanteId, long destinatarioId) {
        return amizadeRepository.ehAmigo(
                solicitanteId,
                destinatarioId);
   }
}
