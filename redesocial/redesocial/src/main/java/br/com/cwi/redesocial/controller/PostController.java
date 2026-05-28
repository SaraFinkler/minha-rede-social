package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.controller.request.post.AlterarPostRequest;
import br.com.cwi.redesocial.controller.request.post.IncluirPostRequest;
import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.service.ListarPostService;
import br.com.cwi.redesocial.service.post.AlterarPostService;
import br.com.cwi.redesocial.service.post.IncluirPostService;
import br.com.cwi.redesocial.service.post.RemoverPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private ListarPostService listarPostService;

    @Autowired
    private IncluirPostService incluirPostService;

    @Autowired
    private AlterarPostService alterarPostService;

    @Autowired
    private RemoverPostService removerPostService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> listar() {
        return listarPostService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse incluir(@Valid @RequestBody IncluirPostRequest request) {
        return incluirPostService.incluir(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse alterar(@Valid @RequestBody AlterarPostRequest request, @PathVariable long id) {
        return alterarPostService.alterar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable long id) {
        removerPostService.remover(id);
    }
}