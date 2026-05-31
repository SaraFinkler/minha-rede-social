package br.com.cwi.redesocial.service.usuario;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para ListarUsuariosService")
class ListarUsuariosServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @InjectMocks
    private ListarUsuariosService listarUsuariosService;

    private Usuario usuarioAutenticado;
    private Usuario outrousuario;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        usuarioAutenticado = UsuarioFactory.getUsuario();
        usuarioAutenticado.setId(1L);

        outrousuario = UsuarioFactory.getUsuario();
        outrousuario.setId(2L);
        outrousuario.setNomeCompleto("João Silva");
        outrousuario.setEmail("joao@example.com");

        pageable = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("Deve listar usuários com busca retornando resultados")
    void deveListarUsuariosComBuscaRetornandoResultados() {
        // Arrange
        String busca = "joão";
        when(usuarioAutenticadoService.get()).thenReturn(usuarioAutenticado);
        when(usuarioRepository.buscarPorNomeOuEmail(busca, usuarioAutenticado.getId(), pageable))
                .thenReturn(new PageImpl<>(List.of(outrousuario)));

        // Act
        Page<UsuarioResponse> resultado = listarUsuariosService.listar(busca, pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        UsuarioResponse response = resultado.getContent().get(0);
        assertEquals(outrousuario.getId(), response.getId());
        assertEquals("João Silva", response.getNomeCompleto());
        assertEquals("joao@example.com", response.getEmail());

        verify(usuarioAutenticadoService, times(1)).get();
        verify(usuarioRepository, times(1)).buscarPorNomeOuEmail(busca, usuarioAutenticado.getId(), pageable);
    }

    @Test
    @DisplayName("Deve retornar página vazia quando nenhum usuário for encontrado")
    void deveRetornarPaginaVaziaQuandoNenhumUsuarioForEncontrado() {
        // Arrange
        String busca = "usuario_inexistente";
        when(usuarioAutenticadoService.get()).thenReturn(usuarioAutenticado);
        when(usuarioRepository.buscarPorNomeOuEmail(busca, usuarioAutenticado.getId(), pageable))
                .thenReturn(new PageImpl<>(List.of()));

        // Act
        Page<UsuarioResponse> resultado = listarUsuariosService.listar(busca, pageable);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        assertEquals(0, resultado.getTotalElements());

        verify(usuarioAutenticadoService, times(1)).get();
        verify(usuarioRepository, times(1)).buscarPorNomeOuEmail(busca, usuarioAutenticado.getId(), pageable);
    }
}

