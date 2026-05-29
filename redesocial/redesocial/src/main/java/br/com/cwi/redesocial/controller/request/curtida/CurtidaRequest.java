package br.com.cwi.redesocial.controller.request.curtida;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CurtidaRequest {
    @NotNull
    long postId;
}
