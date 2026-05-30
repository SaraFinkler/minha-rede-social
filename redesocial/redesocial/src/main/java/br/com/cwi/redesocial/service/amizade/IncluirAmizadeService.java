package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.controller.request.amizade.IncluirAmizadeRequest;
import br.com.cwi.redesocial.controller.response.amizade.AmizadeResponse;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.StatusAmizade;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import br.com.cwi.redesocial.service.usuario.BuscarUsuarioService;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import br.com.cwi.redesocial.validator.amizade.IncluirAmizadeValidator;
import br.com.cwi.redesocial.validator.amizade.SolicitacaoAmizadeValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.cwi.redesocial.mapper.amizade.AmizadeMapper.toResponse;
import static br.com.cwi.redesocial.mapper.amizade.IncluirAmizadeMapper.toEntity;

@Service
public class IncluirAmizadeService {

    @Autowired
    private AmizadeRepository amizadeRepository;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private SolicitacaoAmizadeValidator solicitacaoAmizadeValidator;

    @Autowired
    private IncluirAmizadeValidator incluirAmizadeValidator;

    @Transactional
    public AmizadeResponse incluir(IncluirAmizadeRequest request) {

        Usuario solicitante = usuarioAutenticadoService.get();

        Usuario destinatario =
                buscarUsuarioService.porId(request.getDestinatarioId());

        solicitacaoAmizadeValidator.validar(
                solicitante.getId(),
                destinatario.getId()
        );

        Optional<Amizade> amizadeExistente =
                amizadeRepository.encontrarAmizade(
                        solicitante.getId(),
                        destinatario.getId()
                );

        if (amizadeExistente.isPresent()) {

            Amizade amizade = amizadeExistente.get();

           incluirAmizadeValidator.validar(amizade.getStatus());

            amizade.setStatus(StatusAmizade.PENDENTE);
            amizade.setDataRespostaSolicitacao(null);

            amizadeRepository.save(amizade);

            return toResponse(amizade);
        }

        Amizade amizade = toEntity();

        amizade.setSolicitante(solicitante);
        amizade.setDestinatario(destinatario);
        amizade.setStatus(StatusAmizade.PENDENTE);

        amizadeRepository.save(amizade);

        return toResponse(amizade);
    }
}