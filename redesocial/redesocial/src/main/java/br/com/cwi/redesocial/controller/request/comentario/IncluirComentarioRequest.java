package br.com.cwi.redesocial.controller.request.comentario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncluirComentarioRequest {
    @NotBlank
    @NotNull
    private String conteudo;
}
