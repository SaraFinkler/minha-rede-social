package br.com.cwi.redesocial.controller.request.amizade;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class IncluirAmizadeRequest {
    @NotNull(message = "destinatarioId é obrigatório")
    private Long destinatarioId;
}