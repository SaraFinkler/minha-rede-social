package br.com.cwi.redesocial.mapper.amizade;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.enums.StatusAmizade;

public class IncluirAmizadeMapper {
    public static Amizade toEntity() {
        return Amizade.builder()
                .status(StatusAmizade.PENDENTE)
                .build();
    }
}
