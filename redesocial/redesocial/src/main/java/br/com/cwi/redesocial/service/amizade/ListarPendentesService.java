package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.controller.response.amizade.AmizadeResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.usuario.AmizadeMapper;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarPendentesService {

    @Autowired
    private AmizadeRepository amizadeRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public Page<AmizadeResponse> listarPendentes(Pageable pageable) {

        Usuario usuario = usuarioAutenticadoService.get();

        return amizadeRepository
                .listarAmizadesPendentes(usuario.getId(), pageable)
                .map(AmizadeMapper::toResponse);    }
}
