package br.com.cwi.redesocial.repository;

import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUsuarioInAndAtivoTrueOrderByDataCriacaoDesc(
            List<Usuario> usuarios,
            Pageable pageable
    );
}
