package br.com.cwi.redesocial.controller.request.comentario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncluirComentarioRequest {
    @NotBlank(message = "conteudo é não pode ser vazio")
    @NotNull(message = "conteudo é obrigatório")
    @Size(max = 512, message = "conteudo deve conter no máximo 512 caracteres")
    private String conteudo;
}
