package br.com.cwi.redesocial.controller.request.curtida;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurtidaRequest {
    @NotNull
    private Long postId;
}
