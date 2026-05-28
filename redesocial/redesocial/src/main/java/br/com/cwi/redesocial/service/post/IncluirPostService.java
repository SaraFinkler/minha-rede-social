package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.controller.request.post.IncluirPostRequest;
import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.PostMapper;
import br.com.cwi.redesocial.mapper.post.IncluirPostMapper;
import br.com.cwi.redesocial.repository.PostRepository;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IncluirPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public PostResponse incluir(IncluirPostRequest request) {
        Post post = IncluirPostMapper.toEntity(request);

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));

        post.setAtivo(true);
        post.setUsuario(usuario);

        postRepository.save(post);

        return PostMapper.toResponse(post);
    }
}
