package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.controller.request.amizade.AtualizarStatusAmizadeRequest;
import br.com.cwi.redesocial.controller.response.amizade.AmizadeResponse;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import br.com.cwi.redesocial.validator.amizade.AtualizarStatusValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.redesocial.mapper.amizade.AmizadeMapper.toResponse;

@Service
public class AtualizarStatusAmizadeService {

    @Autowired
    AmizadeService amizadeService;

    @Autowired
    UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    AmizadeRepository amizadeRepository;

    @Autowired
    AtualizarStatusValidator atualizarStatusValidator;

    @Transactional
    public AmizadeResponse atualizarStatus(AtualizarStatusAmizadeRequest request) {
        Amizade amizade = amizadeService.porId(request.getId());
        Usuario usuario = usuarioAutenticadoService.get();

        atualizarStatusValidator.validar(amizade, usuario.getId(), request.getStatus());

        amizade.setStatus(request.getStatus());
        amizadeRepository.save(amizade);

        return toResponse(amizade);
    }
}
