package br.com.cwi.redesocial.repository;

import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
        SELECT p
        FROM Post p
        WHERE p.ativo = true
        AND (
            p.visibilidade = br.com.cwi.redesocial.enums.VisibilidadePost.PUBLICO
            OR p.usuario.id = :usuarioId
            OR (
                p.visibilidade = br.com.cwi.redesocial.enums.VisibilidadePost.PRIVADO
                AND p.usuario IN :amigos
            )
        )
        ORDER BY p.dataCriacao DESC
    """)
    Page<Post> buscarFeed(Long usuarioId, List<Usuario> amigos, Pageable pageable);
}
