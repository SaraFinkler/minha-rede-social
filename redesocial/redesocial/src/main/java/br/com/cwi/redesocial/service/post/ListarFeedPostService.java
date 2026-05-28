package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.PostMapper;
import br.com.cwi.redesocial.repository.PostRepository;
import br.com.cwi.redesocial.service.usuario.BuscarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListarFeedPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    public Page<PostResponse> listarFeed(Long usuarioId, Pageable pageable) {
        Usuario usuario = buscarUsuarioService.porId(usuarioId);

        List<Usuario> usuariosFeed = new ArrayList<>();

        usuariosFeed.add(usuario);

        usuariosFeed.addAll(usuario.getAmigos());

        return postRepository
                .findByUsuarioInAndAtivoTrueOrderByDataCriacaoDesc(
                        usuariosFeed,
                        pageable
                )
                .map(PostMapper::toResponse);
    }
}
