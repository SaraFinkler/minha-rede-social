package br.com.cwi.redesocial.factory;

import br.com.cwi.redesocial.controller.request.amizade.AtualizarStatusAmizadeRequest;
import br.com.cwi.redesocial.enums.StatusAmizade;

public class AtualizarStatusAmizadeRequestFactory {

    public static AtualizarStatusAmizadeRequest criar() {
        AtualizarStatusAmizadeRequest request = new AtualizarStatusAmizadeRequest();
        request.setId(1L);
        request.setStatus(StatusAmizade.ACEITA);
        return request;
    }

    public static AtualizarStatusAmizadeRequest criar(Long id, StatusAmizade status) {
        AtualizarStatusAmizadeRequest request = new AtualizarStatusAmizadeRequest();
        request.setId(id);
        request.setStatus(status);
        return request;
    }

    public static AtualizarStatusAmizadeRequest criarParaAceitar() {
        return criar(1L, StatusAmizade.ACEITA);
    }

    public static AtualizarStatusAmizadeRequest criarParaRecusar() {
        return criar(1L, StatusAmizade.RECUSADA);
    }
}

