package br.com.cwi.redesocial.validator.amizade;

import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.redesocial.factory.AmizadeFactory.criar;
import static br.com.cwi.redesocial.factory.UsuarioFactory.getUsuario;
import static org.junit.jupiter.api.Assertions.*;

class PermiteRemoverValidatorTest {

    private final PermiteRemoverValidator tested = new PermiteRemoverValidator();

    @Test
    @DisplayName("não deve lançar exceção quando usuário for solicitante")
    void naoDeveLancarExcecaoQuandoUsuarioForSolicitante() {

        Usuario usuario = getUsuario();

        Amizade amizade = criar();
        amizade.setSolicitante(usuario);

        assertDoesNotThrow(() ->
                tested.validar(amizade, usuario)
        );
    }

    @Test
    @DisplayName("não deve lançar exceção quando usuário for destinatário")
    void naoDeveLancarExcecaoQuandoUsuarioForDestinatario() {

        Usuario usuario = getUsuario();

        Amizade amizade = criar();
        amizade.setDestinatario(usuario);

        assertDoesNotThrow(() ->
                tested.validar(amizade, usuario)
        );
    }

    @Test
    @DisplayName("deve lançar exceção quando usuário não tiver permissão")
    void deveLancarExcecaoQuandoUsuarioNaoTiverPermissao() {

        Usuario usuario = Usuario.builder()
                .id(99L)
                .build();

        Amizade amizade = criar();

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tested.validar(amizade, usuario)
        );

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals(
                "Você não tem permissão para remover esta amizade",
                exception.getReason()
        );
    }
}