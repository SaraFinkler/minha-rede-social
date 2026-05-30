package br.com.cwi.redesocial.service.amizade;

import br.com.cwi.redesocial.domain.Amizade;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.AmizadeRepository;
import br.com.cwi.redesocial.service.usuario.UsuarioAutenticadoService;
import br.com.cwi.redesocial.validator.amizade.PermiteRemoverValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoverAmizadeService {

    @Autowired
    private AmizadeRepository amizadeRepository;

    @Autowired
    private AmizadeService amizadeService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private PermiteRemoverValidator permiteRemoverValidator;

    @Transactional
    public void remover(long id) {
        Amizade amizade = amizadeService.porId(id);

        Usuario usuario = usuarioAutenticadoService.get();

        permiteRemoverValidator.validar(amizade, usuario);

        amizadeRepository.delete(amizade);
    }
}
