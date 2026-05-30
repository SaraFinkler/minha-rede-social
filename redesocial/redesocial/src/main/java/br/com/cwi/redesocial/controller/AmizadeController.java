package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.controller.request.amizade.AtualizarStatusAmizadeRequest;
import br.com.cwi.redesocial.controller.request.amizade.IncluirAmizadeRequest;
import br.com.cwi.redesocial.controller.response.amizade.AmizadeResponse;
import br.com.cwi.redesocial.controller.response.amizade.ListarAmizadeResponse;
import br.com.cwi.redesocial.service.amizade.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/amizades")
public class AmizadeController {

    @Autowired
    private IncluirAmizadeService incluirAmizadeService;

    @Autowired
    private ListarAmigosService listarAmigosService;

    @Autowired
    private ListarPendentesService listarPendentesService;

    @Autowired
    private RemoverAmizadeService removerAmizadeService;

    @Autowired
    private AtualizarStatusAmizadeService atualizarStatusAmizadeService;

    @GetMapping("/amigos")
    @ResponseStatus(HttpStatus.OK)
    public Page<ListarAmizadeResponse> listarAmigos(@RequestParam(required = false, name = "q") String busca, Pageable pageable) {
        return listarAmigosService.listarAmigos(busca, pageable);
    }

    @GetMapping("/pendentes")
    @ResponseStatus(HttpStatus.OK)
    public Page<AmizadeResponse> listarPendentes(Pageable pageable) {
        return listarPendentesService.listarPendentes(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AmizadeResponse incluir(@Valid @RequestBody IncluirAmizadeRequest request) {
        return incluirAmizadeService.incluir(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AmizadeResponse mudarStatus(@Valid @RequestBody AtualizarStatusAmizadeRequest request) {
        return atualizarStatusAmizadeService.atualizarStatus(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable long id) {
        removerAmizadeService.remover(id);
    }
}
