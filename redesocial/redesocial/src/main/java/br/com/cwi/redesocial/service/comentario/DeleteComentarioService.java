package br.com.cwi.redesocial.service.comentario;

import br.com.cwi.redesocial.domain.Comentario;
import br.com.cwi.redesocial.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteComentarioService {
    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public void deletar(Long id){
        Comentario comentario = comentarioService.porId(id);
        comentarioRepository.delete(comentario);
    }
}
