package br.com.cwi.redesocial.controller.response.comentario;

import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    private LocalDateTime dataAlteracao;

    private Long postId;

    private Long usuarioId;
}
