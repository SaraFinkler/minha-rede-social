package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.controller.request.post.AlterarPostRequest;
import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.PostRepository;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import br.com.cwi.redesocial.validator.post.UsuarioDonoPostValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.redesocial.mapper.PostMapper.toResponse;
import static br.com.cwi.redesocial.mapper.post.AlterarPostMapper.*;

@Service
public class AlterarPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioDonoPostValidator usuarioDonoPostValidator;

    @Transactional
    public PostResponse alterar(long id, AlterarPostRequest request) {
        Post post = postService.porId(id);

        Usuario usuario = usuarioAutenticadoService.get();

        usuarioDonoPostValidator.validar(usuario, post);

        toEntity(post, request);

        postRepository.save(post);

        return toResponse(post);
    }
}
