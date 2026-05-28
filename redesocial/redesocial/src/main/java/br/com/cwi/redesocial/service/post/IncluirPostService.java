package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.controller.request.post.IncluirPostRequest;
import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.PostRepository;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.redesocial.mapper.PostMapper.toResponse;
import static br.com.cwi.redesocial.mapper.post.IncluirPostMapper.toEntity;

@Service
public class IncluirPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Transactional
    public PostResponse incluir(IncluirPostRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();

        Post post = toEntity(request);

        post.setUsuario(usuario);

        postRepository.save(post);

        return toResponse(post);
    }
}
