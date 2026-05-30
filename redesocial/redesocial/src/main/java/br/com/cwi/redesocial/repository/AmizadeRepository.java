package br.com.cwi.redesocial.repository;

import br.com.cwi.redesocial.domain.Amizade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AmizadeRepository extends JpaRepository<Amizade, Long> {

    @Query("""
            SELECT a
            FROM Amizade a
            WHERE a.status = br.com.cwi.redesocial.enums.StatusAmizade.ACEITA
                AND (a.solicitante.id = :usuarioId
                    OR a.destinatario.id = :usuarioId)
            ORDER BY a.id DESC
            """)
    Page<Amizade> listarAmizadesAceitas(@Param("usuarioId") long usuarioId, Pageable pageable);

    @Query("""
            SELECT a
            FROM Amizade a
            WHERE a.status = br.com.cwi.redesocial.enums.StatusAmizade.PENDENTE
                AND (a.solicitante.id = :usuarioId
                    OR a.destinatario.id = :usuarioId)
            ORDER BY a.id DESC
            """)
    Page<Amizade> listarAmizadesPendentes(@Param("usuarioId") long usuarioId, Pageable pageable);

    @Query("""
            SELECT a
            FROM Amizade a
            WHERE a.status = br.com.cwi.redesocial.enums.StatusAmizade.ACEITA
                AND (a.solicitante.id = :usuarioId
                    OR a.destinatario.id = :usuarioId)
                AND (:busca is null or :busca = ''
                    or lower(CASE WHEN a.solicitante.id = :usuarioId
                        THEN a.destinatario.nomeCompleto
                        ELSE a.solicitante.nomeCompleto END) like lower(concat('%', :busca, '%'))
                    or lower(CASE WHEN a.solicitante.id = :usuarioId
                        THEN a.destinatario.email
                        ELSE a.solicitante.email END) like lower(concat('%', :busca, '%')))
            ORDER BY a.id DESC
            """)
    Page<Amizade> listarAmizadesAceitasComBusca(@Param("usuarioId") long usuarioId, @Param("busca") String busca, Pageable pageable);

    @Query("""
            SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
            FROM Amizade a
            WHERE a.status = br.com.cwi.redesocial.enums.StatusAmizade.ACEITA
                AND ((a.solicitante.id = :solicitanteId AND a.destinatario.id = :destinatarioId)
                    OR (a.solicitante.id = :destinatarioId AND a.destinatario.id = :solicitanteId))
            """)
    boolean ehAmigo(@Param("solicitanteId") long solicitanteId,
                    @Param("destinatarioId") long destinatarioId);

    @Query("""
            SELECT a
            FROM Amizade a
            WHERE
                (a.solicitante.id = :usuario1 AND a.destinatario.id = :usuario2)
                OR
                (a.solicitante.id = :usuario2 AND a.destinatario.id = :usuario1)
            """)
    Optional<Amizade> encontrarAmizade(
            Long usuario1,
            Long usuario2
    );
}

