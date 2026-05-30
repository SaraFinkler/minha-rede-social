package br.com.cwi.redesocial.service.usuario;

import br.com.cwi.redesocial.controller.request.usuario.EditarUsarioRequest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para EditarUsuarioService")
class EditarUsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @InjectMocks
    private EditarUsuarioService editarUsuarioService;

    private EditarUsarioRequest request;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        request = new EditarUsarioRequest();
        usuario = UsuarioFactory.getUsuario();
    }

    @Test
    @DisplayName("Deve editar usuário com sucesso quando campos forem preenchidos")
    void deveEditarUsuarioComSucessoQuandoCamposForemPreenchidos() {
        // Arrange
        request.setNomeCompleto("Sara Updated");
        request.setApelido("sara_updated");
        request.setImagemPerfil("https://example.com/nova-foto.jpg");

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UsuarioResponse response = editarUsuarioService.editar(request);

        // Assert
        assertNotNull(response);
        assertEquals("Sara Updated", response.getNomeCompleto());
        assertEquals("sara_updated", response.getApelido());
        assertEquals("https://example.com/nova-foto.jpg", response.getImagemPerfil());
        assertEquals(usuario.getEmail(), response.getEmail());

        verify(usuarioAutenticadoService, times(1)).get();
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    @DisplayName("Deve manter campos originais quando request tiver valores em branco ou nulos")
    void deveManterlCamposOriginaisQuandoRequestTiverValoresEmBrancoOuNulos() {
        // Arrange
        request.setNomeCompleto(null);
        request.setApelido("  ");
        request.setImagemPerfil("");

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String nomeOriginal = usuario.getNomeCompleto();
        String apelidoOriginal = usuario.getApelido();
        String imagemOriginal = usuario.getImagemPerfil();

        // Act
        UsuarioResponse response = editarUsuarioService.editar(request);

        // Assert
        assertNotNull(response);
        assertEquals(nomeOriginal, response.getNomeCompleto());
        assertEquals(apelidoOriginal, response.getApelido());
        assertEquals(imagemOriginal, response.getImagemPerfil());

        verify(usuarioAutenticadoService, times(1)).get();
        verify(usuarioRepository, times(1)).save(usuario);
    }
}

