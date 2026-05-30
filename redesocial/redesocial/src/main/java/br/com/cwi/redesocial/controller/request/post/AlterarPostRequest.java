package br.com.cwi.redesocial.controller.request.post;

import br.com.cwi.redesocial.enums.VisibilidadePost;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarPostRequest {
    @NotNull
    private VisibilidadePost visibilidade;
}
