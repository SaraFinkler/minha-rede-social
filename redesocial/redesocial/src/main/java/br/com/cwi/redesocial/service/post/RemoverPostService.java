package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.PostRepository;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import br.com.cwi.redesocial.validator.post.UsuarioDonoPostValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoverPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioDonoPostValidator usuarioDonoPostValidator;

    @Transactional
    public void remover(long id) {
        Post post = postService.porId(id);

        Usuario usuario = usuarioAutenticadoService.get();

        usuarioDonoPostValidator.validar(usuario, post);

        post.setAtivo(false);

        postRepository.save(post);
    }
}
