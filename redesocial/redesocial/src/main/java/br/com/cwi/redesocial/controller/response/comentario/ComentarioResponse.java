package br.com.cwi.redesocial.controller.response.comentario;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ComentarioResponse {
    private Long id;

    private String conteudo;

    private LocalDateTime dataCriacao;

    private Long postId;

    private Long usuarioId;
}
