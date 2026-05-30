package br.com.cwi.redesocial.service.comentario;

import br.com.cwi.redesocial.controller.request.comentario.IncluirComentarioRequest;
import br.com.cwi.redesocial.controller.response.comentario.ComentarioResponse;
import br.com.cwi.redesocial.domain.Comentario;
import br.com.cwi.redesocial.domain.Post;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.VisibilidadePost;
import br.com.cwi.redesocial.mapper.ComentarioMapper;
import br.com.cwi.redesocial.repository.ComentarioRepository;
import br.com.cwi.redesocial.service.amizade.SaoAmigosService;
import br.com.cwi.redesocial.service.post.PostService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.redesocial.mapper.ComentarioMapper.toResponse;

@Service
public class IncluirComentarioService {
    @Autowired
    private PostService postService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private SaoAmigosService saoAmigosService;

    public ComentarioResponse incluir(Long id, IncluirComentarioRequest request){
        Post post = postService.porId(id);

        Usuario usuario = usuarioAutenticadoService.get();

        boolean usuariosSaoAmigos = saoAmigosService.saoAmigos(usuario.getId(), post.getUsuario().getId());

        if(post.getVisibilidade() == VisibilidadePost.PRIVADO && !usuariosSaoAmigos){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Você precisa ser amigo deste usuário para interagir com ele");
        }

        Comentario comentario = ComentarioMapper.toEntity(request, post, usuario);
        comentarioRepository.save(comentario);

        return toResponse(comentario);
    }
}
