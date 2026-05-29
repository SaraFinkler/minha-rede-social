package br.com.cwi.redesocial.controller.response.amizade;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ListarAmizadeResponse {
    public Long id;
    public String nomeAmigo;
    public String emailAmigo;

}
