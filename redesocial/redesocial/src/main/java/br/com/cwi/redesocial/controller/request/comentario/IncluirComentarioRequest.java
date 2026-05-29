package br.com.cwi.redesocial.controller.request.comentario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncluirComentarioRequest {
    @NotBlank
    @Size(max = 512)
    private String conteudo;
}
