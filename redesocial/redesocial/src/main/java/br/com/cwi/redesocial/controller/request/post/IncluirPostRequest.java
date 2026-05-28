package br.com.cwi.redesocial.controller.request.post;

import br.com.cwi.redesocial.enums.VisibilidadePost;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncluirPostRequest {
    @NotNull
    private Long usuarioId;

    @NotBlank
    @Size(max = 512)
    private String conteudo;

    @NotNull
    private VisibilidadePost visibilidade;
}
