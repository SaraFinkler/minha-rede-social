package br.com.cwi.redesocial.service.login;

import br.com.cwi.redesocial.controller.response.login.LoginResponse;
import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.factory.UsuarioFactory;
import br.com.cwi.redesocial.service.usuario.BuscarUsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para LoginService")
class LoginServiceTest {

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtEncoder jwtEncoder;

    @InjectMocks
    private LoginService loginService;

    @Test
    @DisplayName("Deve realizar login com sucesso")
    void deveRealizarLoginComSucesso() {
        // Arrange
        String email = "sara@email.com";
        String senha = "123456";

        UsuarioResponse usuarioResponse = UsuarioResponse.builder()
                .id(1L)
                .email(email)
                .nomeCompleto("Sara Finkler")
                .build();

        Usuario usuario = UsuarioFactory.getUsuario();
        usuario.setId(1L);
        usuario.setEmail(email);
        usuario.setNomeCompleto("Sara Finkler");
        usuario.setSenha("senha-criptografada");

        Jwt jwt = new Jwt(
                "token.jwt",
                Instant.now(),
                Instant.now().plusSeconds(12),
                Map.of("alg", "none"),
                Map.of("sub", usuario.getNomeCompleto(), "email", usuario.getEmail())
        );

        when(buscarUsuarioService.buscarPorEmail(email)).thenReturn(usuarioResponse);
        when(buscarUsuarioService.porId(usuarioResponse.getId())).thenReturn(usuario);
        when(passwordEncoder.matches(senha, usuario.getSenha())).thenReturn(true);
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        // Act
        ResponseEntity<LoginResponse> response = loginService.login(email, senha);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("token.jwt", response.getBody().accessToken());
        assertEquals(12000L, response.getBody().expiresIn());

        verify(buscarUsuarioService, times(1)).buscarPorEmail(email);
        verify(buscarUsuarioService, times(1)).porId(usuarioResponse.getId());
        verify(passwordEncoder, times(1)).matches(senha, usuario.getSenha());
        verify(jwtEncoder, times(1)).encode(any(JwtEncoderParameters.class));
    }

    @Test
    @DisplayName("Deve lançar BadCredentialsException quando a senha estiver incorreta")
    void deveLancarBadCredentialsQuandoSenhaIncorreta() {
        // Arrange
        String email = "sara@email.com";
        String senha = "senha-incorreta";

        UsuarioResponse usuarioResponse = UsuarioResponse.builder()
                .id(1L)
                .email(email)
                .nomeCompleto("Sara Finkler")
                .build();

        Usuario usuario = UsuarioFactory.getUsuario();
        usuario.setId(1L);
        usuario.setEmail(email);
        usuario.setNomeCompleto("Sara Finkler");
        usuario.setSenha("senha-criptografada");

        when(buscarUsuarioService.buscarPorEmail(email)).thenReturn(usuarioResponse);
        when(buscarUsuarioService.porId(usuarioResponse.getId())).thenReturn(usuario);
        when(passwordEncoder.matches(senha, usuario.getSenha())).thenReturn(false);

        // Act & Assert
        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> loginService.login(email, senha));

        assertEquals("Usuário ou senha incorretos!", exception.getMessage());
        verify(buscarUsuarioService, times(1)).buscarPorEmail(email);
        verify(buscarUsuarioService, times(1)).porId(usuarioResponse.getId());
        verify(passwordEncoder, times(1)).matches(senha, usuario.getSenha());
        verify(jwtEncoder, never()).encode(any());
    }
}

