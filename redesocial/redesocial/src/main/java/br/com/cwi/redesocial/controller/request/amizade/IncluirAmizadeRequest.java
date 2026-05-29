package br.com.cwi.redesocial.controller.request.amizade;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncluirAmizadeRequest {
    @NotNull(message = "DestinatarioId é obrigatório")
    private Long destinatarioId;
}