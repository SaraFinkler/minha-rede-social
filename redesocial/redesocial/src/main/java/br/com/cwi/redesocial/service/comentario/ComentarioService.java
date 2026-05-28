package br.com.cwi.redesocial.service.comentario;

import br.com.cwi.redesocial.domain.Comentario;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.repository.ComentarioRepository;
import br.com.cwi.redesocial.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    public Comentario porId(long id) {
        return comentarioRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentário não encontrado"));
    }
}
