package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RemoverPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Transactional
    public void remover(long id) {
        Post post = postService.porId(id);

        if (!post.getAtivo()) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Não é possível remover post desativado"
            );
        }

        post.setAtivo(false);

        postRepository.save(post);
    }
}
