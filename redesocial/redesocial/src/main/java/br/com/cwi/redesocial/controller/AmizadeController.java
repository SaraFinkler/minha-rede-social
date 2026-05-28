package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.controller.request.IncluirAmizadeRequest;
import br.com.cwi.redesocial.controller.response.AmizadeResponse;
import br.com.cwi.redesocial.service.amizade.IncluirAmizadeService;
import br.com.cwi.redesocial.service.amizade.ListarAmigosService;
import br.com.cwi.redesocial.service.amizade.ListarPendentesService;
import br.com.cwi.redesocial.service.amizade.RemoverAmizadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/amizade")
public class AmizadeController {

    @Autowired
    private IncluirAmizadeService incluirAmizadeService;

    @Autowired
    private ListarAmigosService listarAmigosService;

    @Autowired
    private ListarPendentesService listarPendentesService;

    @Autowired
    private RemoverAmizadeService removerAmizadeService;

    @GetMapping("/amigos")
    @ResponseStatus(HttpStatus.OK)
    public Page<AmizadeResponse> listarAmigos(Pageable pageable) {
        return listarAmigosService.listarAmigos(pageable);
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

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable long id) {
        removerAmizadeService.remover(id);
    }
}
