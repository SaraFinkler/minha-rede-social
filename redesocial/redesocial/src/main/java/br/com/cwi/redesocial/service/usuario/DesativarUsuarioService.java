package br.com.cwi.redesocial.service.usuario;

import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.redesocial.mapper.usuario.UsuarioMapper.toResponse;

@Service
public class DesativarUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Transactional
    public UsuarioResponse desativar() {
        Usuario usuarioLogado = usuarioAutenticadoService.get();
        usuarioLogado.setAtivo(false);
        usuarioRepository.save(usuarioLogado);
        return toResponse(usuarioLogado);
    }
}
