package br.com.cwi.redesocial.service.curtida;

import br.com.cwi.redesocial.controller.request.curtida.CurtidaRequest;
import br.com.cwi.redesocial.domain.Curtida;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.curtida.CurtidaMapper;
import br.com.cwi.redesocial.repository.CurtidaRepository;
import br.com.cwi.redesocial.service.post.PostService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoverCurtidaService {
    @Autowired
    private PostService postService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private CurtidaRepository curtidaRepository;

    public void deletar(CurtidaRequest request) {
        Post post = postService.porId(request.getPostId());
        Usuario usuario = usuarioAutenticadoService.get();

        boolean jaCurtiu = curtidaRepository.existsByUsuarioIdAndPostId(usuario.getId(), request.getPostId());

        if(!jaCurtiu)
            throw new RuntimeException("Você ainda não curtiu este post");

        Curtida curtida = CurtidaMapper.toEntity(post, usuario);
        curtidaRepository.delete(curtida);
    }
}
