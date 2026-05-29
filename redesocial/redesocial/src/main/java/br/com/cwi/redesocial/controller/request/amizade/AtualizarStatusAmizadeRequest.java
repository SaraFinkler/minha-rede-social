package br.com.cwi.redesocial.controller.request.amizade;

import br.com.cwi.redesocial.enums.StatusAmizade;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarStatusAmizadeRequest {
    @NotNull
    private long id;

    @NotNull(message = "status é obrigatório")
    private StatusAmizade status;
}
