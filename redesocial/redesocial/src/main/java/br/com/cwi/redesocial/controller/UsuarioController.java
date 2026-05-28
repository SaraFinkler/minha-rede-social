package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.controller.request.usuario.UsuarioRequest;
import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.service.usuario.IncluirUsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IncluirUsuarioService incluirUsuarioService;

    public UsuarioController(IncluirUsuarioService incluirUsuarioService) {
        this.incluirUsuarioService = incluirUsuarioService;
    }

    @PostMapping
    public UsuarioResponse incluir(@Valid @RequestBody UsuarioRequest request) {
        return incluirUsuarioService.incluir(request);
    }
}
