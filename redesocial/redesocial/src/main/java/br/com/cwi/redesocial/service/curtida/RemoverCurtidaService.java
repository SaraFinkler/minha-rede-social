package br.com.cwi.redesocial.service.curtida;

import br.com.cwi.redesocial.controller.request.curtida.CurtidaRequest;
import br.com.cwi.redesocial.domain.Curtida;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.CurtidaRepository;
import br.com.cwi.redesocial.service.amizade.SaoAmigosService;
import br.com.cwi.redesocial.service.post.PostService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoverCurtidaService {
    @Autowired
    private PostService postService;

    @Autowired
    private CurtidaService curtidaService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private SaoAmigosService saoAmigosService;

    @Autowired
    private CurtidaRepository curtidaRepository;

    @Transactional
    public void deletar(CurtidaRequest request) {
        Post post = postService.porId(request.getPostId());
        Usuario usuario = usuarioAutenticadoService.get();

        curtidaService.validarSeEhAmigo(usuario,post);
        curtidaService.validarNaoCurtiu(usuario, post);

        Curtida curtida = curtidaRepository.findByUsuarioIdAndPostId(usuario.getId(), request.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao localizar curtida para deletar"));

        curtidaRepository.delete(curtida);
    }
}