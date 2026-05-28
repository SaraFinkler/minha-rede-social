package br.com.cwi.redesocial.service.comentario;

import br.com.cwi.redesocial.controller.request.comentario.IncluirComentarioRequest;
import br.com.cwi.redesocial.controller.response.comentario.ComentarioResponse;
import br.com.cwi.redesocial.domain.Comentario;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.comentario.ComentarioMapper;
import br.com.cwi.redesocial.repository.ComentarioRepository;
import br.com.cwi.redesocial.service.post.PostService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncluirComentarioService {
    @Autowired
    private PostService postService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public ComentarioResponse incluir(Long id, IncluirComentarioRequest request){
        Post post = postService.porId(id);

        Usuario usuario = usuarioAutenticadoService.get();

        Comentario comentario = ComentarioMapper.toEntity(request, post, usuario);
        comentarioRepository.save(comentario);

        return ComentarioMapper.toResponse(comentario);
    }
}
