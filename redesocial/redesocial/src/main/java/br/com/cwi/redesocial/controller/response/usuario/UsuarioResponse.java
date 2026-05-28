package br.com.cwi.redesocial.controller.response.usuario;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private boolean ativo;
}
