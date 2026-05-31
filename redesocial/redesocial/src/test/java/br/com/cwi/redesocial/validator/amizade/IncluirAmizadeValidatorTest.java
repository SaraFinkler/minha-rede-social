package br.com.cwi.redesocial.validator.amizade;

import br.com.cwi.redesocial.enums.StatusAmizade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class IncluirAmizadeValidatorTest {

    private final IncluirAmizadeValidator tested = new IncluirAmizadeValidator();

    @Test
    @DisplayName("deve lançar exceção quando status for diferente de recusada")
    void deveLancarExcecaoQuandoStatusForDiferenteDeRecusada() {

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tested.validar(StatusAmizade.PENDENTE)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals(
                "Já existe amizade ou solicitação entre esses usuários",
                exception.getReason()
        );
    }

    @Test
    @DisplayName("não deve lançar exceção quando status for recusada")
    void naoDeveLancarExcecaoQuandoStatusForRecusada() {

        assertDoesNotThrow(
                () -> tested.validar(StatusAmizade.RECUSADA)
        );
    }
}