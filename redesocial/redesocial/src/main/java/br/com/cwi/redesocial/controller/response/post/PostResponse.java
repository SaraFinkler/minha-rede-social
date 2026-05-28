package br.com.cwi.redesocial.controller.response.post;

import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.VisibilidadePost;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostResponse {
    private Long id;

    private String conteudo;

    private VisibilidadePost visibilidade;

    private Long usuario;

    private LocalDateTime dataCriacao;
}
