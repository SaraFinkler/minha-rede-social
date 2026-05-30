package br.com.cwi.redesocial.service.usuario;

import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.factory.UsuarioFactory;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para UsuarioAutenticadoService")
class UsuarioAutenticadoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Deve retornar o email do JWT quando autenticado corretamente")
    void deveRetornarEmailQuandoAutenticadoCorretamente() {
        // Arrange
        Jwt jwt = jwtComEmail();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getCredentials()).thenReturn(jwt);
        setAuthentication(authentication);

        // Act
        String email = usuarioAutenticadoService.getEmail();

        // Assert
        assertEquals("sara@email.com", email);
    }

    @Test
    @DisplayName("Deve lançar UNAUTHORIZED quando não houver autenticação")
    void deveLancarUnauthorizedQuandoNaoHouverAutenticacao() {
        // Arrange
        SecurityContextHolder.clearContext();

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioAutenticadoService.getEmail());

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Usuário não está autenticado", exception.getReason());
    }

    @Test
    @DisplayName("Deve lançar UNAUTHORIZED quando o credential não for JWT")
    void deveLancarUnauthorizedQuandoCredentialNaoForJwt() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getCredentials()).thenReturn("token-invalido");
        setAuthentication(authentication);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioAutenticadoService.getEmail());

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Token JWT inválido", exception.getReason());
    }

    @Test
    @DisplayName("Deve lançar UNAUTHORIZED quando o JWT não tiver claim de email")
    void deveLancarUnauthorizedQuandoJwtNaoTiverEmail() {
        // Arrange
        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "Sara Finkler")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60))
                .build();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getCredentials()).thenReturn(jwt);
        setAuthentication(authentication);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioAutenticadoService.getEmail());

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Token JWT inválido", exception.getReason());
    }

    @Test
    @DisplayName("Deve retornar o usuário autenticado quando ele existir no banco")
    void deveRetornarUsuarioAutenticadoQuandoExistirNoBanco() {
        // Arrange
        Usuario usuario = UsuarioFactory.getUsuario();
        jwtEContexto();
        when(usuarioRepository.findByEmail("sara@email.com")).thenReturn(Optional.of(usuario));

        // Act
        Usuario resultado = usuarioAutenticadoService.get();

        // Assert
        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        assertEquals(usuario.getEmail(), resultado.getEmail());
        verify(usuarioRepository, times(1)).findByEmail("sara@email.com");
    }

    @Test
    @DisplayName("Deve lançar UNAUTHORIZED quando o usuário autenticado não existir no banco")
    void deveLancarUnauthorizedQuandoUsuarioNaoExistirNoBanco() {
        // Arrange
        jwtEContexto();
        when(usuarioRepository.findByEmail("sara@email.com")).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioAutenticadoService.get());

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Usuário não autenticado", exception.getReason());
        verify(usuarioRepository, times(1)).findByEmail("sara@email.com");
    }

    private void jwtEContexto() {
        Jwt jwt = jwtComEmail();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getCredentials()).thenReturn(jwt);
        setAuthentication(authentication);
    }

    private Jwt jwtComEmail() {
        return Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("email", "sara@email.com")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60))
                .build();
    }

    private void setAuthentication(Authentication authentication) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}





