package br.com.cwi.redesocial.controller.request.amizade;

import br.com.cwi.redesocial.enums.StatusAmizade;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarStatusAmizadeRequest {
    @NotNull
    private Long id;

    @NotNull
    private StatusAmizade status;
}
