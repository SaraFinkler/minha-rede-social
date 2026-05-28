package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.service.MensagemPrivadaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exemplos")
public class ExemploController {

    private final MensagemPrivadaService mensagemPrivadaService;

    public ExemploController(MensagemPrivadaService mensagemPrivadaService) {
        this.mensagemPrivadaService = mensagemPrivadaService;
    }

    @GetMapping
    public String privado() {
        return "Rota privada";
    }

    @GetMapping("/publico")
    public String publico() {
        return "Rota pública";
    }
}
