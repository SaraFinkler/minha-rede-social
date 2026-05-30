package br.com.cwi.redesocial.mapper.amizade;

import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.enums.StatusAmizade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IncluirAmizadeMapperTest {

    @Test
    @DisplayName("deve mapear entity com status pendente")
    void deveMapearEntityComStatusPendente() {
        Amizade amizade = IncluirAmizadeMapper.toEntity();

        assertEquals(StatusAmizade.PENDENTE, amizade.getStatus());
    }
}