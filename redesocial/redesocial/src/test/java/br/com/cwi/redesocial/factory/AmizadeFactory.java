package br.com.cwi.redesocial.factory;

import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.StatusAmizade;

import java.time.LocalDate;

public class AmizadeFactory {

    public static Amizade getAmizade() {
        return Amizade.builder()
                .id(1L)
                .status(StatusAmizade.PENDENTE)
                .solicitante(getSolicitante())
                .destinatario(getDestinatario())
                .build();
    }

    private static Usuario getSolicitante() {
        return Usuario.builder()
                .id(1L)
                .nomeCompleto("Solicitante")
                .apelido("solicitante")
                .email("solicitante@email.com")
                .senha("123456")
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .ativo(true)
                .build();
    }

    private static Usuario getDestinatario() {
        return Usuario.builder()
                .id(2L)
                .nomeCompleto("Destinatario")
                .apelido("destinatario")
                .email("destinatario@email.com")
                .senha("123456")
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .ativo(true)
                .build();
    }
}