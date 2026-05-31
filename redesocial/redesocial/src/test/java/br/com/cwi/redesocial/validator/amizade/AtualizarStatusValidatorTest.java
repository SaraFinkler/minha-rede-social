package br.com.cwi.redesocial.validator.amizade;

import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.StatusAmizade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class AtualizarStatusValidatorTest {

    private final AtualizarStatusValidator tested = new AtualizarStatusValidator();

    @Test
    @DisplayName("deve lançar exceção quando solicitante tentar atualizar para status diferente de recusada")
    void deveLancarExcecaoQuandoSolicitanteAtualizarParaStatusDiferenteDeRecusada() {

        Usuario solicitante = new Usuario();
        solicitante.setId(1L);

        Amizade amizade = new Amizade();
        amizade.setSolicitante(solicitante);
        amizade.setStatus(StatusAmizade.PENDENTE);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tested.validar(amizade, 1L, StatusAmizade.ACEITA)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals(
                "Usuário solicitante não pode atualizar status da amizade",
                exception.getReason()
        );
    }

    @Test
    @DisplayName("deve lançar exceção quando tentar atualizar para mesmo status")
    void deveLancarExcecaoQuandoAtualizarParaMesmoStatus() {

        Usuario solicitante = new Usuario();
        solicitante.setId(1L);

        Amizade amizade = new Amizade();
        amizade.setSolicitante(solicitante);
        amizade.setStatus(StatusAmizade.ACEITA);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tested.validar(amizade, 2L, StatusAmizade.ACEITA)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals(
                "Status da amizade não pode ser alterado para o mesmo status ou para pendente",
                exception.getReason()
        );
    }

    @Test
    @DisplayName("deve lançar exceção quando tentar atualizar para pendente")
    void deveLancarExcecaoQuandoAtualizarParaPendente() {

        Usuario solicitante = new Usuario();
        solicitante.setId(1L);

        Amizade amizade = new Amizade();
        amizade.setSolicitante(solicitante);
        amizade.setStatus(StatusAmizade.ACEITA);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tested.validar(amizade, 2L, StatusAmizade.PENDENTE)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals(
                "Status da amizade não pode ser alterado para o mesmo status ou para pendente",
                exception.getReason()
        );
    }

    @Test
    @DisplayName("não deve lançar exceção quando solicitante atualizar para recusada")
    void naoDeveLancarExcecaoQuandoSolicitanteAtualizarParaRecusada() {

        Usuario solicitante = new Usuario();
        solicitante.setId(1L);

        Amizade amizade = new Amizade();
        amizade.setSolicitante(solicitante);
        amizade.setStatus(StatusAmizade.PENDENTE);

        assertDoesNotThrow(
                () -> tested.validar(amizade, 1L, StatusAmizade.RECUSADA)
        );
    }

    @Test
    @DisplayName("não deve lançar exceção quando atualização for válida")
    void naoDeveLancarExcecaoQuandoAtualizacaoForValida() {

        Usuario solicitante = new Usuario();
        solicitante.setId(1L);

        Amizade amizade = new Amizade();
        amizade.setSolicitante(solicitante);
        amizade.setStatus(StatusAmizade.PENDENTE);

        assertDoesNotThrow(
                () -> tested.validar(amizade, 2L, StatusAmizade.ACEITA)
        );
    }
}