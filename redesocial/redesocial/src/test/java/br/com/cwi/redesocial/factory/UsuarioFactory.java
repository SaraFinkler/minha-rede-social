package br.com.cwi.redesocial.factory;

import br.com.cwi.redesocial.domain.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioFactory {

    public static Usuario criar() {
        return Usuario.builder()
                .id(1L)
                .nomeCompleto("João Silva")
                .email("joao@example.com")
                .apelido("joao")
                .dataNascimento(LocalDate.of(1990, 1, 15))
                .senha("senha123")
                .imagemPerfil("https://example.com/imagem.jpg")
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .dataAlteracao(LocalDateTime.now())
                .build();
    }

    public static Usuario criar(Long id, String nomeCompleto, String email) {
        Usuario usuario = criar();
        usuario.setId(id);
        usuario.setNomeCompleto(nomeCompleto);
        usuario.setEmail(email);
        return usuario;
    }

    public static Usuario criar(String nomeCompleto, String email) {
        return criar(1L, nomeCompleto, email);
    }
}

