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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para DesativarUsuarioService")
class DesativarUsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @InjectMocks
    private DesativarUsuarioService desativarUsuarioService;

    @Test
    @DisplayName("Deve desativar usuário autenticado com sucesso")
    void deveDesativarUsuarioComSucesso() {
        // Arrange
        Usuario usuario = UsuarioFactory.getUsuario();
        usuario.setAtivo(true);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UsuarioResponse response = desativarUsuarioService.desativar();

        // Assert
        assertNotNull(response);
        assertFalse(usuario.isAtivo());
        assertFalse(response.isAtivo());
        assertEquals(usuario.getEmail(), response.getEmail());

        verify(usuarioAutenticadoService, times(1)).get();
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    @DisplayName("Deve retornar dados corretos do usuário desativado")
    void deveRetornarDadosCorretosdoUsuarioDesativado() {
        // Arrange
        Usuario usuario = UsuarioFactory.getUsuario();
        usuario.setAtivo(true);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UsuarioResponse response = desativarUsuarioService.desativar();

        // Assert
        assertNotNull(response);
        assertEquals(usuario.getId(), response.getId());
        assertEquals(usuario.getNomeCompleto(), response.getNomeCompleto());
        assertEquals(usuario.getEmail(), response.getEmail());
        assertEquals(usuario.getApelido(), response.getApelido());
        assertEquals(usuario.getDataNascimento(), response.getDataNascimento());
        assertEquals(usuario.getImagemPerfil(), response.getImagemPerfil());
    }
}


