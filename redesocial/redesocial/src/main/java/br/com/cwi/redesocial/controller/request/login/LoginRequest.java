package br.com.cwi.redesocial.controller.request.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    @Email
    private String email;

    @NotNull(message = "senha é obrigatória")
    @NotBlank(message = "senha não pode ser vazia")
    private String senha;
}
