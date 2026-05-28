package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.controller.request.IncluirAmizadeRequest;
import br.com.cwi.redesocial.controller.response.AmizadeResponse;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import br.com.cwi.redesocial.service.usuario.BuscarUsuarioService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import br.com.cwi.redesocial.validator.amizade.SolicitacaoAmizadeValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.redesocial.mapper.usuario.AmizadeMapper.toResponse;
import static br.com.cwi.redesocial.mapper.usuario.IncluirAmizadeMapper.*;

@Service
public class IncluirAmizadeService {

    @Autowired
    private AmizadeRepository amizadeRepository;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private ExisteAmizadeService existeAmizadeService;

    @Autowired
    private SolicitacaoAmizadeValidator solicitacaoAmizadeValidator;

    @Transactional
    public AmizadeResponse incluir(IncluirAmizadeRequest request) {
        Usuario solicitante = usuarioAutenticadoService.get();
        Usuario destinatario = buscarUsuarioService.porId(request.getDestinatarioId());

        existeAmizadeService.existeAmizade(solicitante.getId(), destinatario.getId());
        solicitacaoAmizadeValidator.validar(solicitante.getId(), destinatario.getId());

        Amizade amizade = toEntity(request);

        amizade.setSolicitante(solicitante);
        amizade.setDestinatario(destinatario);

        amizadeRepository.save(amizade);

        return toResponse(amizade);
    }
}
