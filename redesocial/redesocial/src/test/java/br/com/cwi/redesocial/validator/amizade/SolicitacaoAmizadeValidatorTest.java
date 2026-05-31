package br.com.cwi.redesocial.validator.amizade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class SolicitacaoAmizadeValidatorTest {

    private final SolicitacaoAmizadeValidator tested =
            new SolicitacaoAmizadeValidator();

    @Test
    @DisplayName("deve validar solicitacao de amizade")
    void deveValidarSolicitacaoDeAmizade() {

        assertDoesNotThrow(() ->
                tested.validar(1L, 2L)
        );
    }

    @Test
    @DisplayName("deve lancar excecao quando usuario solicitar amizade para si mesmo")
    void deveLancarExcecaoQuandoUsuarioSolicitarAmizadeParaSiMesmo() {

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tested.validar(1L, 1L)
        );

        assertEquals(
                "400 BAD_REQUEST \"O usuário não pode solicitar amizade com ele mesmo\"",
                exception.getMessage()
        );
    }
}
