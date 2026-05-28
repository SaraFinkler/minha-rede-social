package br.com.cwi.redesocial.service.usuario;

import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.usuario.UsuarioMapper;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return UsuarioMapper.toResponse(usuarioLogado);
    }
}
