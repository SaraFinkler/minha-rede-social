package br.com.cwi.redesocial.service.comentario;

import br.com.cwi.redesocial.controller.response.comentario.ComentarioResponse;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.mapper.comentario.ComentarioMapper;
import br.com.cwi.redesocial.repository.ComentarioRepository;
import br.com.cwi.redesocial.service.post.PostService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListaComentariosPostService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;



    public Page<ComentarioResponse> listarComentariosPost(long idPost, Pageable pageable){
        Post post = postService.porId(idPost);

        return comentarioRepository
                .findAllByPostId(
                        idPost,
                        pageable
                )
                .map(ComentarioMapper::toResponse);


    }
}
