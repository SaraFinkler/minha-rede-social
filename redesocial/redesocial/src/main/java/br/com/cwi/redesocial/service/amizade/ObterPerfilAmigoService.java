package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.usuario.UsuarioMapper;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import br.com.cwi.redesocial.service.usuario.BuscarUsuarioService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ObterPerfilAmigoService {

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private AmizadeRepository amizadeRepository;

    public UsuarioResponse obterPerfil(Long amigoId) {
        Usuario usuarioAutenticado = usuarioAutenticadoService.get();
        Usuario amigo = buscarUsuarioService.porId(amigoId);

        boolean saoAmigos = amizadeRepository.ehAmigo(usuarioAutenticado.getId(), amigoId);

        if (!saoAmigos) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para visualizar este perfil");
        }

        return UsuarioMapper.toResponse(amigo);
    }
}

