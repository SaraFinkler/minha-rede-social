package br.com.cwi.redesocial.service.curtida;

import br.com.cwi.redesocial.controller.request.curtida.CurtidaRequest;
import br.com.cwi.redesocial.domain.Curtida;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.VisibilidadePost;
import br.com.cwi.redesocial.mapper.curtida.CurtidaMapper;
import br.com.cwi.redesocial.repository.CurtidaRepository;
import br.com.cwi.redesocial.service.amizade.SaoAmigosService;
import br.com.cwi.redesocial.service.post.PostService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IncluirCurtidaService {
    @Autowired
    private PostService postService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private SaoAmigosService saoAmigosService;

    @Autowired
    private CurtidaRepository curtidaRepository;

    public void incluir(CurtidaRequest request) {
        Post post = postService.porId(request.getPostId());
        Usuario usuario = usuarioAutenticadoService.get();

        boolean usuariosSaoAmigos = saoAmigosService.saoAmigos(usuario.getId(), post.getUsuario().getId());

        if(post.getVisibilidade() == VisibilidadePost.PRIVADO && !usuariosSaoAmigos){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Você precisa ser amigo deste usuário para interagir com ele");
        }

        boolean jaCurtiu = curtidaRepository.existsByUsuarioIdAndPostId(usuario.getId(), request.getPostId());

        if(jaCurtiu)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Você já curtiu este post");

        Curtida curtida = CurtidaMapper.toEntity(post, usuario);
        curtidaRepository.save(curtida);
    }
}
