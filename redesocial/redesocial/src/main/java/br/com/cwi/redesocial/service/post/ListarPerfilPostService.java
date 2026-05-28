package br.com.cwi.redesocial.service.post;

import br.com.cwi.redesocial.controller.response.post.PostPerfilResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.post.PostPerfilMapper;
import br.com.cwi.redesocial.repository.PostRepository;
import br.com.cwi.redesocial.service.usuario.BuscarUsuarioService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarPerfilPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public Page<PostPerfilResponse> listarPerfil(Long pessoaId, Pageable pageable) {
        if (pessoaId != null) {
            buscarUsuarioService.porId(pessoaId);
        }

        Usuario usuario = usuarioAutenticadoService.get();

        return postRepository
                .buscarPerfil(
                        usuario.getId(),
                        pessoaId,
                        pageable
                )
                .map(PostPerfilMapper::toResponse);
    }
}
