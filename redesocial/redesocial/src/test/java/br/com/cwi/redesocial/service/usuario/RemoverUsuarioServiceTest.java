package br.com.cwi.redesocial.service.usuario;

import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.factory.UsuarioFactory;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para RemoverUsuarioService")
class RemoverUsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @InjectMocks
    private RemoverUsuarioService removerUsuarioService;

    @Test
    @DisplayName("Deve remover o usuário autenticado com sucesso")
    void deveRemoverUsuarioAutenticadoComSucesso() {
        // Arrange
        Usuario usuario = UsuarioFactory.getUsuario();
        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        // Act
        removerUsuarioService.remover();

        // Assert
        verify(usuarioAutenticadoService, times(1)).get();
        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    @DisplayName("Deve chamar o repositório delete com o usuário correto")
    void deveCallRepositoryDeleteComUsuarioCorreto() {
        // Arrange
        Usuario usuario = UsuarioFactory.getUsuario();
        usuario.setId(5L);
        usuario.setEmail("teste@example.com");

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        // Act
        removerUsuarioService.remover();

        // Assert
        verify(usuarioRepository, times(1)).delete(usuario);
        verify(usuarioAutenticadoService, times(1)).get();
    }
}

