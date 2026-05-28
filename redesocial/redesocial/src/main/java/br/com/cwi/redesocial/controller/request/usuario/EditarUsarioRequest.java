package br.com.cwi.redesocial.controller.request.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditarUsarioRequest {
    private String nomeCompleto;

    private String apelido;

    private String imagemPerfil;
}
