package br.com.cwi.redesocial.mapper.usuario;

import br.com.cwi.redesocial.controller.request.usuario.UsuarioRequest;
import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.factory.UsuarioFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsuarioMapperTest {

    @Test
    @DisplayName("deve instanciar mapper")
    void deveInstanciarMapper() {
        assertNotNull(new UsuarioMapper());
    }

    @Test
    @DisplayName("deve mapear request para entity")
    void deveMapearRequestParaEntity() {
        UsuarioRequest request = UsuarioFactory.getUsuarioRequest();

        Usuario usuario = UsuarioMapper.toEntity(request);

        assertEquals(request.getNome(), usuario.getNomeCompleto());
        assertEquals(request.getEmail(), usuario.getEmail());
        assertEquals(request.getSenha(), usuario.getSenha());
        assertEquals(request.getDataNascimento(), usuario.getDataNascimento());
    }

    @Test
    @DisplayName("deve mapear entity para response")
    void deveMapearEntityParaResponse() {
        Usuario usuario = UsuarioFactory.getUsuario();

        UsuarioResponse response = UsuarioMapper.toResponse(usuario);

        assertEquals(usuario.getId(), response.getId());
        assertEquals(usuario.getNomeCompleto(), response.getNomeCompleto());
        assertEquals(usuario.getEmail(), response.getEmail());
        assertEquals(usuario.getApelido(), response.getApelido());
        assertEquals(usuario.getDataNascimento(), response.getDataNascimento());
        assertEquals(usuario.getImagemPerfil(), response.getImagemPerfil());
        assertEquals(usuario.isAtivo(), response.isAtivo());
    }
}
