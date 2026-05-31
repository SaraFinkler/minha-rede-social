package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.repository.AmizadeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para SaoAmigosService")
class SaoAmigosServiceTest {

    @Mock
    private AmizadeRepository amizadeRepository;

    @InjectMocks
    private SaoAmigosService saoAmigosService;

    @Test
    @DisplayName("Deve retornar true quando usuários são amigos")
    void deveRetornarTrueQuandoSaoAmigos() {
        // Arrange
        long solicitanteId = 1L;
        long destinatarioId = 2L;
        when(amizadeRepository.ehAmigo(solicitanteId, destinatarioId)).thenReturn(true);

        // Act
        boolean resultado = saoAmigosService.saoAmigos(solicitanteId, destinatarioId);

        // Assert
        assertTrue(resultado);
        verify(amizadeRepository, times(1)).ehAmigo(solicitanteId, destinatarioId);
    }

    @Test
    @DisplayName("Deve retornar false quando usuários não são amigos")
    void deveRetornarFalseQuandoNaoSaoAmigos() {
        // Arrange
        long solicitanteId = 1L;
        long destinatarioId = 3L;
        when(amizadeRepository.ehAmigo(solicitanteId, destinatarioId)).thenReturn(false);

        // Act
        boolean resultado = saoAmigosService.saoAmigos(solicitanteId, destinatarioId);

        // Assert
        assertFalse(resultado);
        verify(amizadeRepository, times(1)).ehAmigo(solicitanteId, destinatarioId);
    }
}

