package br.com.cwi.redesocial.service.usuario;

import br.com.cwi.redesocial.controller.request.usuario.UsuarioRequest;
import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.factory.UsuarioFactory;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para CadastrarUsuarioService")
class CadastrarUsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CadastrarUsuarioService cadastrarUsuarioService;

    private UsuarioRequest request;

    @BeforeEach
    void setUp() {
        request = UsuarioFactory.getUsuarioRequest();
        request.setNome("Sara Finkler");
        request.setEmail("sara@email.com");
        request.setSenha("123456");
        request.setDataNascimento(LocalDate.of(2000, 1, 1));
        request.setApelido("sara");
        request.setImagemPerfil("https://example.com/foto.jpg");
    }

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso")
    void deveCadastrarUsuarioComSucesso() {
        // Arrange
        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getSenha())).thenReturn("senha-criptografada");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UsuarioResponse response = cadastrarUsuarioService.cadastrar(request);

        // Assert
        assertNotNull(response);
        assertEquals(request.getNome(), response.getNomeCompleto());
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals(request.getDataNascimento(), response.getDataNascimento());
        assertTrue(response.isAtivo());

        verify(usuarioRepository, times(1)).findByEmail(request.getEmail());
        verify(passwordEncoder, times(1)).encode(request.getSenha());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando email já estiver cadastrado")
    void deveLancarExcecaoQuandoEmailJaEstiverCadastrado() {
        // Arrange
        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(UsuarioFactory.getUsuario()));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> cadastrarUsuarioService.cadastrar(request));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Já existe um usuário cadastrado com o email informado.", exception.getReason());
        verify(usuarioRepository, times(1)).findByEmail(request.getEmail());
        verify(passwordEncoder, never()).encode(any());
        verify(usuarioRepository, never()).save(any());
    }
}

