package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.controller.request.usuario.UsuarioRequest;
import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.service.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private CadastrarUsuarioService cadastrarUsuarioService;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private DesativarUsuarioService desativarUsuarioService;

    @Autowired
    private RemoverUsuarioService removerUsuarioService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public UsuarioController(CadastrarUsuarioService cadastrarUsuarioService) {
        this.cadastrarUsuarioService = cadastrarUsuarioService;
    }

    @GetMapping("/eu")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse obterUsuarioLogado() { return usuarioAutenticadoService.get(); }


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse buscarPorEmail(@RequestParam String email) {
        return buscarUsuarioService.buscarPorEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrar(@Valid @RequestBody UsuarioRequest request) {
        return cadastrarUsuarioService.cadastrar(request);
    }

    @PutMapping("/desativar")
    @ResponseStatus(NO_CONTENT)
    public void desativarContaUsuarioLogado() {
        desativarUsuarioService.desativar();
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void removerUsuarioLogado() {
        removerUsuarioService.remover();
    }
}
