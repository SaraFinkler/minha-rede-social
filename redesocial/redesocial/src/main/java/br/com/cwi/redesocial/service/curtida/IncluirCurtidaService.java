package br.com.cwi.redesocial.service.curtida;

import br.com.cwi.redesocial.controller.request.curtida.CurtidaRequest;
import br.com.cwi.redesocial.domain.Curtida;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.curtida.CurtidaMapper;
import br.com.cwi.redesocial.repository.CurtidaRepository;
import br.com.cwi.redesocial.service.amizade.SaoAmigosService;
import br.com.cwi.redesocial.service.post.PostService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.redesocial.mapper.curtida.CurtidaMapper.toEntity;

@Service
public class IncluirCurtidaService {
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
    public void incluir(CurtidaRequest request) {
        Post post = postService.porId(request.getPostId());
        Usuario usuario = usuarioAutenticadoService.get();

        curtidaService.validarSeEhAmigo(usuario,post);
        curtidaService.validarJaCurtiu(usuario, post);

        Curtida curtida = toEntity(post, usuario);
        curtidaRepository.save(curtida);
    }
}
