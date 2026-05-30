package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.controller.request.comentario.IncluirComentarioRequest;
import br.com.cwi.redesocial.controller.response.comentario.ComentarioResponse;
import br.com.cwi.redesocial.service.comentario.*;
import br.com.cwi.redesocial.service.post.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private DeleteComentarioService deleteComentarioService;

    @Autowired
    private IncluirComentarioService incluirComentarioService;

    @Autowired
    private BuscarComentarioService buscarComentarioService;

    @Autowired
    private ListaComentariosPostService listaComentariosPostService;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioResponse criarComentario(@PathVariable long id, @Valid @RequestBody IncluirComentarioRequest request) {
        return incluirComentarioService.incluir(id, request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ComentarioResponse buscarComentario(@PathVariable long id){
        return buscarComentarioService.buscarComentario(id);
    }

    @GetMapping("/post/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ComentarioResponse> listarComentariosPost(@PathVariable long id, Pageable pageable){
        return listaComentariosPostService.listarComentariosPost(id, pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarComentario(@PathVariable long id) {
        deleteComentarioService.deletar(id);
    }
}
