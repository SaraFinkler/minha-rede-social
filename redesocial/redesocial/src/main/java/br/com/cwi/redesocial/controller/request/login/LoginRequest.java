package br.com.cwi.redesocial.controller.request.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotNull(message = "email é obrigatório")
    @Email(message = "email deve ser um endereço de email válido")
    private String email;

    @NotNull(message = "senha é obrigatória")
    @NotBlank(message = "senha não pode ser vazia")
    private String senha;
}
