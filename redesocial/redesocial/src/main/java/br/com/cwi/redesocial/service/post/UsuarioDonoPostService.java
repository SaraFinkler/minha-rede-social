package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioDonoPostService {

    @Autowired
    private UsuarioDonoPostService usuarioDonoPostService;

    public void validator(Usuario usuario, Post post) {
        if (post.getUsuario() != usuario) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Só o usuário dono do post pode remover o post"
            );
        }
    }
}
