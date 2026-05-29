package br.com.cwi.redesocial.controller.request.curtida;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CurtidaRequest {
    @NotNull(message = "postId é obrigatório")
    @NotEmpty(message = "postId é não pode ser vazio")
    Long postId;
}
