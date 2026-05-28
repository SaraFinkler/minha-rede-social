package br.com.cwi.redesocial.service.comentario;

import br.com.cwi.redesocial.controller.response.comentario.ComentarioResponse;
import br.com.cwi.redesocial.mapper.comentario.ComentarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscarComentarioService {
    @Autowired
    private ComentarioService comentarioService;

    public ComentarioResponse buscarComentario(long id){
        return ComentarioMapper.toResponse(comentarioService.porId(id));
    }
}
