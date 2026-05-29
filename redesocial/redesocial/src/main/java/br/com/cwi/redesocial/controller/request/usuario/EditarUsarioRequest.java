package br.com.cwi.redesocial.controller.request.usuario;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditarUsarioRequest {

    @Size(max = 255, message = "nomeCompleto deve conter no máximo 255 caracteres")
    private String nomeCompleto;

    @Size(max = 50, message = "apelido deve conter no máximo 50 caracteres")
    private String apelido;

    @Size(max = 512, message = "imagemPerfil deve conter no máximo 512 caracteres")
    private String imagemPerfil;
}
