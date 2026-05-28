package br.com.cwi.redesocial.service;

import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.springframework.stereotype.Service;

@Service
public class MensagemPrivadaService {

    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public MensagemPrivadaService(UsuarioAutenticadoService usuarioAutenticadoService) {
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    public String gerarMensagem() {
        return "Rota privada: " + usuarioAutenticadoService.get().getNomeCompleto();
    }
}
