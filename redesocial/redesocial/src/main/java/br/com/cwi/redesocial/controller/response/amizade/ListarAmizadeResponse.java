package br.com.cwi.redesocial.controller.response.amizade;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ListarAmizadeResponse {
    private Long id;
    private String nomeAmigo;
    private String emailAmigo;
}
