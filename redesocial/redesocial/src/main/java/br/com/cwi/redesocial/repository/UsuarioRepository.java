package br.com.cwi.redesocial.repository;

import br.com.cwi.redesocial.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    @Query("""
            select u
            from Usuario u
            where (:busca is null or :busca = ''
                   or lower(u.nomeCompleto) like lower(concat('%', :busca, '%'))
                   or lower(u.email) like lower(concat('%', :busca, '%')))
            and u.id != :usuarioAutenticadoId
            """)
    Page<Usuario> buscarPorNomeOuEmail(@Param("busca") String busca, @Param("usuarioAutenticadoId") Long usuarioAutenticadoId, Pageable pageable);
}
