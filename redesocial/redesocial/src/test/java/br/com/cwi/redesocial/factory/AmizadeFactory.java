package br.com.cwi.redesocial.factory;

import br.com.cwi.redesocial.controller.request.amizade.AtualizarStatusAmizadeRequest;
import br.com.cwi.redesocial.controller.request.amizade.IncluirAmizadeRequest;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.StatusAmizade;

import java.time.LocalDateTime;

public final class AmizadeFactory {

    private AmizadeFactory() {
    }

    public static Amizade criar() {
        return Amizade.builder()
                .id(1L)
                .solicitante(criarUsuario(1L, "João", "joao@example.com"))
                .destinatario(criarUsuario(2L, "Maria", "maria@example.com"))
                .status(StatusAmizade.PENDENTE)
                .dataCriacao(LocalDateTime.now())
                .dataRespostaSolicitacao(null)
                .build();
    }

    private static Usuario criarUsuario(Long id, String nomeCompleto, String email) {
        Usuario usuario = UsuarioFactory.getUsuario();
        usuario.setId(id);
        usuario.setNomeCompleto(nomeCompleto);
        usuario.setEmail(email);
        return usuario;
    }

    public static IncluirAmizadeRequest criarIncluirRequest(long destinatarioId) {
        IncluirAmizadeRequest request = new IncluirAmizadeRequest();
        request.setDestinatarioId(destinatarioId);
        return request;
    }

    public static AtualizarStatusAmizadeRequest criarAtualizarRequest(Long id, StatusAmizade status) {
        AtualizarStatusAmizadeRequest request = new AtualizarStatusAmizadeRequest();
        request.setId(id);
        request.setStatus(status);
        return request;
    }
}
