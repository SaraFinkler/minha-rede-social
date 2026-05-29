package br.com.cwi.redesocial.controller.response.amizade;

import br.com.cwi.redesocial.enums.StatusAmizade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AmizadeResponse {
    private Long id;
    private Long solicitanteId;
    private Long destinatarioId;
    private StatusAmizade status;
}
