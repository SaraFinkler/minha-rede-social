package br.com.cwi.redesocial.repository;

import br.com.cwi.redesocial.domain.Comentario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    Page<Comentario> findAllByPostId(Long postId, Pageable pageable);
}
