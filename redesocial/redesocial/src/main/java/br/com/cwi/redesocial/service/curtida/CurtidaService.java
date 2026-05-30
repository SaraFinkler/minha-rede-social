package br.com.cwi.redesocial.service.curtida;

import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.VisibilidadePost;
import br.com.cwi.redesocial.repository.CurtidaRepository;
import br.com.cwi.redesocial.service.amizade.SaoAmigosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CurtidaService {
    @Autowired
    private SaoAmigosService saoAmigosService;

    @Autowired
    private CurtidaRepository curtidaRepository;

    public void validarJaCurtiu(Usuario usuario, Post post) {

        if (jaCurtiu(usuario, post)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Você já curtiu este post"
            );
        }
    }

    public void validarNaoCurtiu(Usuario usuario, Post post) {

        if (!jaCurtiu(usuario, post)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Você ainda não curtiu este post"
            );
        }
    }

    public void validarSeEhAmigo(Usuario usuario, Post post) {

        boolean postPrivado = post.getVisibilidade() == VisibilidadePost.PRIVADO;

        if (postPrivado &&
                !saoAmigosService.saoAmigos(usuario.getId(), post.getUsuario().getId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Você precisa ser amigo deste usuário para interagir com ele"
            );
        }
    }

    private boolean jaCurtiu(Usuario usuario, Post post) {

        return curtidaRepository.existsByUsuarioIdAndPostId(
                usuario.getId(),
                post.getId()
        );
    }
}
