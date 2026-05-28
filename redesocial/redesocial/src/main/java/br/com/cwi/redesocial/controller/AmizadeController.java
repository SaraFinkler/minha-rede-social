package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.controller.request.IncluirAmizadeRequest;
import br.com.cwi.redesocial.controller.response.AmizadeResponse;
import br.com.cwi.redesocial.service.amizade.IncluirAmizadeService;
import br.com.cwi.redesocial.service.amizade.ListarAmigosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/amizade")
public class AmizadeController {

    @Autowired
    private IncluirAmizadeService incluirAmizadeService;

    @Autowired
    private ListarAmigosService listarAmigosService;

    @GetMapping("/amigos")
    @ResponseStatus(HttpStatus.OK)
    public Page<AmizadeResponse> listarAmigos(Pageable pageable) {
        return listarAmigosService.listarAmigos(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AmizadeResponse incluir(@Valid @RequestBody IncluirAmizadeRequest request) {
        return incluirAmizadeService.incluir(request);
    }
}
