package br.com.cwi.redesocial.validator.post;

import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UsuarioDonoPostValidator {
    public void validar(Usuario usuario, Post post) {
        if (post.getUsuario() != usuario) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Só o usuário dono do post pode remover o post"
            );
        }
    }
}