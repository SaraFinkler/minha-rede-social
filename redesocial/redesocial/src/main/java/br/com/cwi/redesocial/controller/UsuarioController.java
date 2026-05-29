package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.controller.request.usuario.EditarUsarioRequest;
import br.com.cwi.redesocial.controller.request.usuario.UsuarioRequest;
import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.service.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

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
    private EditarUsuarioService editarUsuarioService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse obterUsuarioLogado() { return buscarUsuarioService.obterUsuarioLogado(); }


    @GetMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse buscarPorEmail(@RequestParam String email) {
        return buscarUsuarioService.buscarPorEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrar(@Valid @RequestBody UsuarioRequest request) {
        return cadastrarUsuarioService.cadastrar(request);
    }

    @PutMapping()
    @ResponseStatus(OK)
    public UsuarioResponse editarUsuarioLogado(@Valid @RequestBody EditarUsarioRequest request) {
        return editarUsuarioService.editar(request);
    }

    @PutMapping("/desativar")
    @ResponseStatus(NO_CONTENT)
    public UsuarioResponse desativarContaUsuarioLogado() {
        return desativarUsuarioService.desativar();
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void removerUsuarioLogado() {
        removerUsuarioService.remover();
    }
}
