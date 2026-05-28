package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.controller.request.post.AlterarPostRequest;
import br.com.cwi.redesocial.controller.request.post.IncluirPostRequest;
import br.com.cwi.redesocial.controller.response.post.PostPerfilResponse;
import br.com.cwi.redesocial.controller.response.post.PostResponse;
import br.com.cwi.redesocial.service.post.ListarPostService;
import br.com.cwi.redesocial.service.post.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private ListarPaginadoPostService listarPaginadoPostService;

    @Autowired
    private ListarFeedPostService listarFeedPostService;

    @Autowired
    private ListarPerfilPostService listarPerfilPostService;

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

    @GetMapping("/paginado")
    @ResponseStatus(HttpStatus.OK)
    public Page<PostResponse> listarPaginado(Pageable pageable) {
        return listarPaginadoPostService.listarPaginado(pageable);
    }

    @GetMapping("/feed")
    @ResponseStatus(HttpStatus.OK)
    public Page<PostResponse> listarFeed(Pageable pageable) {
        return listarFeedPostService.listarFeed(pageable);
    }

    @GetMapping("/perfil")
    @ResponseStatus(HttpStatus.OK)
    public Page<PostPerfilResponse> listarPerfil(@RequestParam(required = false) Long pessoaId, Pageable pageable) {
        return listarPerfilPostService.listarPerfil(pessoaId, pageable);
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
