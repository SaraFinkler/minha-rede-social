package br.com.cwi.redesocial.repository;

import br.com.cwi.redesocial.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> { }
