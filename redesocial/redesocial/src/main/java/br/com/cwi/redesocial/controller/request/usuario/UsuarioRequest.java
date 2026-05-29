package br.com.cwi.redesocial.controller.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioRequest {

    @NotBlank
    @Size(max = 255)
    private String nome;

    @NotBlank
    @Email
    @Size(max = 255)
    private String email;

    @Size(max = 50)
    private String apelido;

    @NotNull()
    private LocalDate dataNascimento;

    @NotBlank
    @Size(max = 128)
    private String senha;

    @Size(max = 512)
    private String imagemPerfil;
}