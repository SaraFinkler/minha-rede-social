package br.com.cwi.redesocial.controller.response.post;

import br.com.cwi.redesocial.enums.VisibilidadePost;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostPerfilResponse {

    private Long id;

    private String conteudo;

    private VisibilidadePost visibilidade;

    private boolean ativo;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAlteracao;

    // 👇 DADOS DO AUTOR
    private Long usuarioId;

    private String nomeAutor;

    private String apelidoAutor;
}