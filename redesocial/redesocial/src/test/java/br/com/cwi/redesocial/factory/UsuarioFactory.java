package br.com.cwi.redesocial.factory;

import br.com.cwi.redesocial.controller.request.usuario.UsuarioRequest;
import br.com.cwi.redesocial.domain.Usuario;

import java.time.LocalDate;

public class UsuarioFactory {
    public static Usuario getUsuario() {
        return Usuario.builder()
                .id(1L)
                .nomeCompleto("Sara Finkler")
                .apelido("sara")
                .email("sara@email.com")
                .senha("123456")
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .ativo(true)
                .build();
    }

    public static UsuarioRequest getUsuarioRequest() {
        UsuarioRequest request = new UsuarioRequest();

        request.setNome("Sara Finkler");
        request.setEmail("sara@email.com");
        request.setSenha("123456");
        request.setDataNascimento(LocalDate.of(2000, 1, 1));

        return request;
    }
}