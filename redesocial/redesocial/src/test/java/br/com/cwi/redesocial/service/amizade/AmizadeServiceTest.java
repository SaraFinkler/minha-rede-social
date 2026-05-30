package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.factory.AmizadeFactory;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import org.junit.jupiter.api.BeforeEach;
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
@DisplayName("Testes para AmizadeService")
class AmizadeServiceTest {

    @Mock
    private AmizadeRepository amizadeRepository;

    @InjectMocks
    private AmizadeService amizadeService;

    private Amizade amizade;

    @BeforeEach
    void setUp() {
        amizade = AmizadeFactory.criar();
    }

    @Test
    @DisplayName("Deve retornar dados completos da amizade")
    void deveRetornarDadosCompletosAmizade() {
        // Arrange
        Long id = 1L;
        when(amizadeRepository.findById(id)).thenReturn(Optional.of(amizade));

        // Act
        Amizade resultado = amizadeService.porId(id);

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getSolicitante());
        assertNotNull(resultado.getDestinatario());
        assertNotNull(resultado.getDataCriacao());
        assertEquals(amizade.getSolicitante().getNomeCompleto(), resultado.getSolicitante().getNomeCompleto());
        assertEquals(amizade.getDestinatario().getNomeCompleto(), resultado.getDestinatario().getNomeCompleto());
        verify(amizadeRepository, times(1)).findById(id);
        verifyNoMoreInteractions(amizadeRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção quando amizade não encontrada")
    void deveLancarExcecaoQuandoAmizadeNaoEncontrada() {
        // Arrange
        Long id = 999L;
        when(amizadeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            amizadeService.porId(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Amizade não encontrada"));
        verify(amizadeRepository, times(1)).findById(id);
    }
}



