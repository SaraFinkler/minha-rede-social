package br.com.cwi.redesocial.controller.response.usuario;

import br.com.cwi.redesocial.domain.Permissao;
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
    private List<Permissao> permissoes;
}
