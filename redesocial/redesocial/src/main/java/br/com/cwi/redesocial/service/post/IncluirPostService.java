package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.controller.request.post.IncluirPostRequest;
import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.PostMapper;
import br.com.cwi.redesocial.mapper.post.IncluirPostMapper;
import br.com.cwi.redesocial.repository.PostRepository;
import br.com.cwi.redesocial.service.usuario.BuscarUsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncluirPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Transactional
    public PostResponse incluir(IncluirPostRequest request) {
        Usuario usuario = buscarUsuarioService.porId(request.getUsuarioId());

        Post post = IncluirPostMapper.toEntity(request);

        post.setAtivo(true);
        post.setUsuario(usuario);

        postRepository.save(post);

        return PostMapper.toResponse(post);
    }
}
