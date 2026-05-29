package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.controller.response.amizade.ListarAmizadeResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.amizade.ListarAmizadeMapper;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarAmigosService {

    @Autowired
    private AmizadeRepository amizadeRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public Page<ListarAmizadeResponse> listarAmigos(Pageable pageable) {
        Usuario usuario = usuarioAutenticadoService.get();

        return amizadeRepository
                .listarAmizadesAceitas(usuario.getId(), pageable)
                .map(amizade -> ListarAmizadeMapper.toResponse(amizade, usuario.getId()));
    }

    public Page<ListarAmizadeResponse> listarAmigos(String busca, Pageable pageable) {
        Usuario usuario = usuarioAutenticadoService.get();
        return amizadeRepository
                .listarAmizadesAceitasComBusca(usuario.getId(), busca, pageable)
                .map(amizade -> ListarAmizadeMapper.toResponse(amizade, usuario.getId()));
    }
}