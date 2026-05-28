package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.mapper.PostMapper;
import br.com.cwi.redesocial.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ListarPostService {

    @Autowired
    private PostRepository postRepository;

    public List<PostResponse> listar() {
        return postRepository.findAll().stream()
                .map(PostMapper::toResponse)
                .collect(toList());
    }
}
