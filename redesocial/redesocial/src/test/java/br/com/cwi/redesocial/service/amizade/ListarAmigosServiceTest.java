package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.controller.response.amizade.ListarAmizadeResponse;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
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
@DisplayName("Testes para ListarAmigosService")
class ListarAmigosServiceTest {

    @Mock
    private AmizadeRepository amizadeRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @InjectMocks
    private ListarAmigosService listarAmigosService;

    private Usuario usuarioA;
    private Usuario usuarioB;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        usuarioA = UsuarioFactory.getUsuario();
        usuarioA.setId(1L);
        usuarioA.setNomeCompleto("João");
        usuarioA.setEmail("joao@example.com");

        usuarioB = UsuarioFactory.getUsuario();
        usuarioB.setId(2L);
        usuarioB.setNomeCompleto("Maria");
        usuarioB.setEmail("maria@example.com");
        pageable = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("Deve listar amigos sem filtro de busca")
    void deveListarAmigosSemFiltro() {
        // Arrange: amizade onde o usuário autenticado é o solicitante
        Amizade amizade = AmizadeFactory.criar();
        amizade.setSolicitante(usuarioA);
        amizade.setDestinatario(usuarioB);
        amizade.setId(10L);
        amizade.setStatus(br.com.cwi.redesocial.enums.StatusAmizade.ACEITA);

        when(usuarioAutenticadoService.get()).thenReturn(usuarioA);
        when(amizadeRepository.listarAmizadesAceitas(usuarioA.getId(), pageable))
                .thenReturn(new PageImpl<>(List.of(amizade)));

        // Act
        Page<ListarAmizadeResponse> resultado = listarAmigosService.listarAmigos(pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        ListarAmizadeResponse resp = resultado.getContent().get(0);
        assertEquals(amizade.getId(), resp.getId());
        assertEquals(usuarioB.getNomeCompleto(), resp.getNomeAmigo());
        assertEquals(usuarioB.getEmail(), resp.getEmailAmigo());
        verify(usuarioAutenticadoService, times(1)).get();
        verify(amizadeRepository, times(1)).listarAmizadesAceitas(usuarioA.getId(), pageable);
    }

    @Test
    @DisplayName("Deve listar amigos com filtro de busca")
    void deveListarAmigosComFiltro() {
        // Arrange: amizade onde o usuário autenticado é o destinatario
        Amizade amizade = AmizadeFactory.criar();
        amizade.setSolicitante(usuarioB);
        amizade.setDestinatario(usuarioA);
        amizade.setId(20L);
        amizade.setStatus(br.com.cwi.redesocial.enums.StatusAmizade.ACEITA);

        String busca = "maria";

        when(usuarioAutenticadoService.get()).thenReturn(usuarioA);
        when(amizadeRepository.listarAmizadesAceitasComBusca(usuarioA.getId(), busca, pageable))
                .thenReturn(new PageImpl<>(List.of(amizade)));

        // Act
        Page<ListarAmizadeResponse> resultado = listarAmigosService.listarAmigos(busca, pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        ListarAmizadeResponse resp = resultado.getContent().get(0);
        assertEquals(amizade.getId(), resp.getId());
        assertEquals(usuarioB.getNomeCompleto(), resp.getNomeAmigo());
        assertEquals(usuarioB.getEmail(), resp.getEmailAmigo());
        verify(usuarioAutenticadoService, times(1)).get();
        verify(amizadeRepository, times(1)).listarAmizadesAceitasComBusca(usuarioA.getId(), busca, pageable);
    }

    @Test
    @DisplayName("Deve retornar página vazia quando não houver amigos")
    void deveRetornarPaginaVaziaQuandoNaoHouver() {
        // Arrange
        when(usuarioAutenticadoService.get()).thenReturn(usuarioA);
        when(amizadeRepository.listarAmizadesAceitas(usuarioA.getId(), pageable))
                .thenReturn(new PageImpl<>(List.of()));

        // Act
        Page<ListarAmizadeResponse> resultado = listarAmigosService.listarAmigos(pageable);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(amizadeRepository, times(1)).listarAmizadesAceitas(usuarioA.getId(), pageable);
    }
}