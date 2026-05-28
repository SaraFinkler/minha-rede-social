package br.com.cwi.redesocial.controller.response.usuario;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioResponse {
    private Long id;
    private String nomeCompleto;
    private String email;
    private String apelido;
    private LocalDate dataNascimento;
    private String imagemPerfil;
    private boolean ativo;
}
