package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.mapper.PostMapper;
import br.com.cwi.redesocial.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarPaginadoPostService {

    @Autowired
    private PostRepository postRepository;

    public Page<PostResponse> listarPaginado(Pageable pageable) {
        return postRepository.findByAtivoTrue(pageable)
                .map(PostMapper::toResponse);
    }
}
