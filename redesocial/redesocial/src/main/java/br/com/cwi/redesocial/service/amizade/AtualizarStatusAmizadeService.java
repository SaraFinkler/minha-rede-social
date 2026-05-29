package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.controller.request.amizade.AtualizarStatusAmizadeRequest;
import br.com.cwi.redesocial.controller.response.AmizadeResponse;
import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.enums.StatusAmizade;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.redesocial.mapper.usuario.AmizadeMapper.toResponse;

@Service
public class AtualizarStatusAmizadeService {

    @Autowired
    AmizadeService amizadeService;

    @Autowired
    UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    AmizadeRepository amizadeRepository;

    public AmizadeResponse atualizarStatus(AtualizarStatusAmizadeRequest request) {
        Amizade amizade = amizadeService.porId(request.getId());
        Usuario usuario = usuarioAutenticadoService.get();

        if(amizade.getSolicitante().getId().equals(usuario.getId()) && request.getStatus() != StatusAmizade.RECUSADA) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Usuario solicitante nao pode atualizar status da amizade");
        }

        if(amizade.getStatus() == request.getStatus() || request.getStatus() == StatusAmizade.PENDENTE) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Status da amizade nao pode ser alterado para o mesmo status ou para pendente");
        }
        amizade.setStatus(request.getStatus());
        amizadeRepository.save(amizade);

        return toResponse(amizade);
    }
}
