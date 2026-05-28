package br.com.cwi.redesocial.controller.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioRequest {

    @NotBlank
    private String nome;

    @NotNull
    @Email
    private String email;

    @NotBlank
    private String senha;

    @NotNull
    LocalDate dataNascimento;
}
