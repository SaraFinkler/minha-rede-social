package br.com.cwi.redesocial.mapper.usuario;

import br.com.cwi.redesocial.controller.request.usuario.UsuarioRequest;
import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequest request) {
        return Usuario.builder()
                .nomeCompleto(request.getNome())
                .email(request.getEmail())
                .senha(request.getSenha())
                .dataNascimento(request.getDataNascimento())
                .build();
    }

    public static UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nomeCompleto(usuario.getNomeCompleto())
                .email(usuario.getEmail())
                .apelido(usuario.getApelido())
                .dataNascimento(usuario.getDataNascimento())
                .imagemPerfil(usuario.getImagemPerfil())
                .ativo(usuario.isAtivo())
                .build();
    }
}
