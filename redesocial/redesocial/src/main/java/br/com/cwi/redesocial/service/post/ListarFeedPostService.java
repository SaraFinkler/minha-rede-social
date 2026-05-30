package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.post.PostMapper;
import br.com.cwi.redesocial.repository.PostRepository;
import br.com.cwi.redesocial.service.usuario.BuscarUsuarioService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarFeedPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public Page<PostResponse> listarFeed(Pageable pageable) {
        Usuario usuario = usuarioAutenticadoService.get();

        return postRepository
                .buscarFeed(
                        usuario.getId(),
                        pageable
                )
                .map(PostMapper::toResponse);
    }
}
