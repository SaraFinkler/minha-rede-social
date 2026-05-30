package br.com.cwi.redesocial.repository;

import br.com.cwi.redesocial.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            SELECT p
            FROM Post p
            WHERE (
                p.visibilidade = br.com.cwi.redesocial.enums.VisibilidadePost.PUBLICO
                OR p.usuario.id = :usuarioId
                OR (
                    p.visibilidade = br.com.cwi.redesocial.enums.VisibilidadePost.PRIVADO
                    AND EXISTS (
                        SELECT a.id
                        FROM Amizade a
                        WHERE a.status = br.com.cwi.redesocial.enums.StatusAmizade.ACEITA
                            AND (
                                (a.solicitante.id = :usuarioId AND a.destinatario = p.usuario)
                                OR (a.destinatario.id = :usuarioId AND a.solicitante = p.usuario)
                            )
                    )
                )
            )
            ORDER BY p.dataCriacao DESC
            """)
    Page<Post> buscarFeed(@Param("usuarioId") Long usuarioId, Pageable pageable);

    @Query("""
            SELECT p
            FROM Post p
            WHERE (
                (
                    :pessoaId IS NULL
                    AND p.usuario.id = :usuarioId
                )
                OR (
                    :pessoaId IS NOT NULL
                    AND p.usuario.id = :pessoaId
                    AND (
                        p.visibilidade = br.com.cwi.redesocial.enums.VisibilidadePost.PUBLICO
                        OR (
                            p.visibilidade = br.com.cwi.redesocial.enums.VisibilidadePost.PRIVADO
                            AND EXISTS (
                                SELECT a.id
                                FROM Amizade a
                                WHERE a.status = br.com.cwi.redesocial.enums.StatusAmizade.ACEITA
                                    AND (
                                        (a.solicitante.id = :usuarioId AND a.destinatario.id = :pessoaId)
                                        OR (a.destinatario.id = :usuarioId AND a.solicitante.id = :pessoaId)
                                    )
                            )
                        )
                    )
                )
            )
            ORDER BY p.dataCriacao DESC
            """)
    Page<Post> buscarPerfil(@Param("usuarioId") Long usuarioId, @Param("pessoaId") Long pessoaId, Pageable pageable);
}
