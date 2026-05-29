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

    @NotBlank(message = "nome é obrigatório")
    @Size(max = 255, message = "nome deve conter no máximo 255 caracteres")
    private String nome;

    @NotBlank(message = "email é obrigatório")
    @Email(message = "email deve ser um endereço de email válido")
    @Size(max = 255, message = "email deve conter no máximo 255 caracteres")
    private String email;

    @Size(max = 50, message = "apelido deve conter no máximo 50 caracteres")
    private String apelido;

    @NotNull(message = "dataNascimento é obrigatório")
    private LocalDate dataNascimento;

    @NotBlank(message = "senha é obrigatória")
    @Size(max = 128, message = "senha deve conter no máximo 128 caracteres")
    private String senha;

    @Size(max = 512, message = "imagemPerfil deve conter no máximo 512 caracteres")
    private String imagemPerfil;
}