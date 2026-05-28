package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.mapper.PostMapper;
import br.com.cwi.redesocial.repository.PostRepository;
import br.com.cwi.redesocial.service.usuario.BuscarUsuarioService;
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

    public Page<PostResponse> listarFeed(Long usuarioId, Pageable pageable) {
        buscarUsuarioService.porId(usuarioId);

        return postRepository
                .buscarFeed(
                        usuarioId,
                        pageable
                )
                .map(PostMapper::toResponse);
    }
}
