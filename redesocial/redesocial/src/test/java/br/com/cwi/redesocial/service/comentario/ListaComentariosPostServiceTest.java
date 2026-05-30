package br.com.cwi.redesocial.service.comentario;

import br.com.cwi.redesocial.controller.response.comentario.ComentarioResponse;
import br.com.cwi.redesocial.domain.Comentario;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.factory.ComentarioFactory;
import br.com.cwi.redesocial.factory.PostFactory;
import br.com.cwi.redesocial.repository.ComentarioRepository;
import br.com.cwi.redesocial.service.post.PostService;
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
@DisplayName("Testes para ListaComentariosPostService")
class ListaComentariosPostServiceTest {

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private PostService postService;

    @InjectMocks
    private ListaComentariosPostService listaComentariosPostService;

    private Post post;
    private Comentario comentario1;
    private Comentario comentario2;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        post = PostFactory.getPost();
        post.setId(1L);

        comentario1 = ComentarioFactory.getComentario();
        comentario1.setId(10L);
        comentario1.setPost(post);

        comentario2 = ComentarioFactory.getComentario();
        comentario2.setId(11L);
        comentario2.setPost(post);

        pageable = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("Deve listar comentários do post com paginação")
    void deveListarComentariosComPaginacao() {
        // Arrange
        when(postService.porId(post.getId())).thenReturn(post);
        when(comentarioRepository.findAllByPostId(post.getId(), pageable))
                .thenReturn(new PageImpl<>(List.of(comentario1, comentario2)));

        // Act
        Page<ComentarioResponse> resultado = listaComentariosPostService.listarComentariosPost(post.getId(), pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements());
        assertEquals(2, resultado.getContent().size());
        assertEquals(comentario1.getConteudo(), resultado.getContent().get(0).getConteudo());
        assertEquals(comentario2.getConteudo(), resultado.getContent().get(1).getConteudo());
        verify(postService, times(1)).porId(post.getId());
        verify(comentarioRepository, times(1)).findAllByPostId(post.getId(), pageable);
    }

    @Test
    @DisplayName("Deve retornar página vazia quando post não tem comentários")
    void deveRetornarPaginaVaziaQuandoNaoHaComentarios() {
        // Arrange
        when(postService.porId(post.getId())).thenReturn(post);
        when(comentarioRepository.findAllByPostId(post.getId(), pageable))
                .thenReturn(new PageImpl<>(List.of()));

        // Act
        Page<ComentarioResponse> resultado = listaComentariosPostService.listarComentariosPost(post.getId(), pageable);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        assertEquals(0, resultado.getTotalElements());
        verify(postService, times(1)).porId(post.getId());
        verify(comentarioRepository, times(1)).findAllByPostId(post.getId(), pageable);
    }
}



