package br.com.cwi.redesocial.service.usuario;

import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.factory.UsuarioFactory;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para BuscarUsuarioService")
class BuscarUsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @InjectMocks
    private BuscarUsuarioService buscarUsuarioService;

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void deveBuscarPorIdComSucesso() {
        // Arrange
        long id = 1L;
        Usuario usuario = UsuarioFactory.getUsuario();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // Act
        Usuario resultado = buscarUsuarioService.porId(id);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        assertEquals(usuario.getEmail(), resultado.getEmail());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lançar NOT_FOUND quando usuário por ID não existir")
    void deveLancarNotFoundQuandoUsuarioPorIdNaoExistir() {
        // Arrange
        long id = 999L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> buscarUsuarioService.porId(id));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Usuario não encontrado", exception.getReason());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve buscar usuário por email com sucesso")
    void deveBuscarPorEmailComSucesso() {
        // Arrange
        String email = "sara@email.com";
        Usuario usuario = UsuarioFactory.getUsuario();
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UsuarioResponse resultado = buscarUsuarioService.buscarPorEmail(email);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        assertEquals(usuario.getEmail(), resultado.getEmail());
        assertEquals(usuario.getNomeCompleto(), resultado.getNomeCompleto());
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Deve lançar NOT_FOUND quando usuário por email não existir")
    void deveLancarNotFoundQuandoUsuarioPorEmailNaoExistir() {
        // Arrange
        String email = "inexistente@example.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> buscarUsuarioService.buscarPorEmail(email));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Usuario não encontrado", exception.getReason());
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Deve obter usuário autenticado com sucesso")
    void deveObterUsuarioAutenticadoComSucesso() {
        // Arrange
        Usuario usuario = UsuarioFactory.getUsuario();
        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        // Act
        UsuarioResponse resultado = buscarUsuarioService.obterUsuarioLogado();

        // Assert
        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        assertEquals(usuario.getEmail(), resultado.getEmail());
        assertEquals(usuario.getNomeCompleto(), resultado.getNomeCompleto());
        assertEquals(usuario.isAtivo(), resultado.isAtivo());
        verify(usuarioAutenticadoService, times(1)).get();
    }
}

