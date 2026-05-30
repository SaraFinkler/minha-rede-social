package br.com.cwi.redesocial.factory;

import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.StatusAmizade;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AmizadeFactory {

    public static Amizade criar() {
        Usuario solicitante = UsuarioFactory.criar(1L, "João", "joao@example.com");
        Usuario destinatario = UsuarioFactory.criar(2L, "Maria", "maria@example.com");

        return Amizade.builder()
                .id(1L)
                .solicitante(solicitante)
                .destinatario(destinatario)
                .status(StatusAmizade.PENDENTE)
                .dataCriacao(LocalDateTime.now())
                .dataRespostaSolicitacao(null)
                .build();
    }

    public static Amizade criar(Long id, StatusAmizade status) {
        Amizade amizade = criar();
        amizade.setId(id);
        amizade.setStatus(status);
        return amizade;
    }

    public static Amizade criar(Usuario solicitante, Usuario destinatario, StatusAmizade status) {
        return Amizade.builder()
                .id(1L)
                .solicitante(solicitante)
                .destinatario(destinatario)
                .status(status)
                .dataCriacao(LocalDateTime.now())
                .dataRespostaSolicitacao(null)
                .build();
    }

    public static Amizade criarAceita() {
        Amizade amizade = criar();
        amizade.setStatus(StatusAmizade.ACEITA);
        amizade.setDataRespostaSolicitacao(LocalDateTime.now());
        return amizade;
    }

    public static Amizade criarRecusada() {
        Amizade amizade = criar();
        amizade.setStatus(StatusAmizade.RECUSADA);
        amizade.setDataRespostaSolicitacao(LocalDateTime.now());
        return amizade;
    }

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
