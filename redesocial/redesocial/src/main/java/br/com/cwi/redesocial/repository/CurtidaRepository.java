package br.com.cwi.redesocial.repository;

import br.com.cwi.redesocial.domain.Curtida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurtidaRepository extends JpaRepository<Curtida, Long> {
    boolean existsByUsuarioIdAndPostId(Long idUsuario, Long idPost);
}
