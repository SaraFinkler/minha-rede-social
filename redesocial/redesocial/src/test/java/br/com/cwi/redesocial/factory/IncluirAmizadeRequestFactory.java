package br.com.cwi.redesocial.factory;

import br.com.cwi.redesocial.controller.request.amizade.IncluirAmizadeRequest;

import java.lang.reflect.Field;

public class IncluirAmizadeRequestFactory {

    public static IncluirAmizadeRequest criar() {
        return criar(2L);
    }

    public static IncluirAmizadeRequest criar(long destinatarioId) {
        IncluirAmizadeRequest request = new IncluirAmizadeRequest();
        definirDestinatarioId(request, destinatarioId);
        return request;
    }

    private static void definirDestinatarioId(IncluirAmizadeRequest request, long destinatarioId) {
        try {
            Field field = IncluirAmizadeRequest.class.getDeclaredField("destinatarioId");
            field.setAccessible(true);
            field.setLong(request, destinatarioId);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Erro ao criar IncluirAmizadeRequest de teste", e);
        }
    }
}

