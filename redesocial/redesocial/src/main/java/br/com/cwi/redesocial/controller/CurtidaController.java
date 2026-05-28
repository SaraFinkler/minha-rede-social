package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.controller.request.curtida.CurtidaRequest;
import br.com.cwi.redesocial.service.curtida.IncluirCurtidaService;
import br.com.cwi.redesocial.service.curtida.RemoverCurtidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/curtida")
public class CurtidaController {

    @Autowired
    IncluirCurtidaService incluirCurtidaService;

    @Autowired
    RemoverCurtidaService removerCurtidaService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void incluirCurtida(@RequestBody CurtidaRequest request) {
        incluirCurtidaService.incluir(request);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerCurtida(@RequestBody CurtidaRequest request) {
        removerCurtidaService.deletar(request);
    }
}
