package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.controller.response.amizade.AmizadeResponse;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.StatusAmizade;
import br.com.cwi.redesocial.factory.AmizadeFactory;
import br.com.cwi.redesocial.factory.UsuarioFactory;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
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
@DisplayName("Testes para ListarPendentesService")
class ListarPendentesServiceTest {

    @Mock
    private AmizadeRepository amizadeRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @InjectMocks
    private ListarPendentesService listarPendentesService;

    private Usuario usuario;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        usuario = UsuarioFactory.getUsuario();
        usuario.setId(1L);
        pageable = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("Deve listar amizades pendentes com sucesso")
    void deveListarPendentesComSucesso() {
        // Arrange
        Amizade amizade1 = AmizadeFactory.criar();
        amizade1.setId(1L);
        amizade1.setStatus(StatusAmizade.PENDENTE);
        amizade1.setDestinatario(usuario);

        Amizade amizade2 = AmizadeFactory.criar();
        amizade2.setId(2L);
        amizade2.setStatus(StatusAmizade.PENDENTE);
        amizade2.setDestinatario(usuario);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(amizadeRepository.listarAmizadesPendentes(usuario.getId(), pageable))
                .thenReturn(new PageImpl<>(List.of(amizade1, amizade2)));

        // Act
        Page<AmizadeResponse> resultado = listarPendentesService.listarPendentes(pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements());
        assertTrue(resultado.getContent().stream()
                .allMatch(resp -> resp.getStatus() == StatusAmizade.PENDENTE));
        verify(usuarioAutenticadoService, times(1)).get();
        verify(amizadeRepository, times(1)).listarAmizadesPendentes(usuario.getId(), pageable);
    }

    @Test
    @DisplayName("Deve retornar página vazia quando não houver pendentes")
    void deveRetornarPaginaVaziaQuandoNaoHouver() {
        // Arrange
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(amizadeRepository.listarAmizadesPendentes(usuario.getId(), pageable))
                .thenReturn(new PageImpl<>(List.of()));

        // Act
        Page<AmizadeResponse> resultado = listarPendentesService.listarPendentes(pageable);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(amizadeRepository, times(1)).listarAmizadesPendentes(usuario.getId(), pageable);
    }
}

