package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.controller.request.usuario.UsuarioRequest;
import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.security.service.BuscarUsuarioService;
import br.com.cwi.redesocial.security.service.IncluirUsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IncluirUsuarioService incluirUsuarioService;
    private final BuscarUsuarioService buscarUsuarioService;

    public UsuarioController(IncluirUsuarioService incluirUsuarioService, BuscarUsuarioService buscarUsuarioService) {
        this.incluirUsuarioService = incluirUsuarioService;
        this.buscarUsuarioService = buscarUsuarioService;
    }

    @PostMapping
    public UsuarioResponse incluir(@Valid @RequestBody UsuarioRequest request) {
        return incluirUsuarioService.incluir(request);
    }

    @GetMapping("/me")
    public UsuarioResponse buscar(){
        return buscarUsuarioService.buscar();
    }
}
