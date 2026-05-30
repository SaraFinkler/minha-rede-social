package br.com.cwi.redesocial.controller.request.usuario;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditarUsarioRequest {

    @Size(max = 255)
    private String nomeCompleto;

    @Size(max = 50)
    private String apelido;

    @Size(max = 512)
    private String imagemPerfil;
}
