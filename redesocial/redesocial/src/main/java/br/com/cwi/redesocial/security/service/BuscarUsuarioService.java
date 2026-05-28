package br.com.cwi.redesocial.security.service;

import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import br.com.cwi.redesocial.service.UsuarioAutenticadoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.cwi.redesocial.mapper.usuario.UsuarioMapper.toResponse;

@Service
public class BuscarUsuarioService {

    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final UsuarioRepository usuarioRepository;

    public BuscarUsuarioService(UsuarioAutenticadoService usuarioAutenticadoService, UsuarioRepository usuarioRepository){
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse buscar(){
        Usuario usuarioAutenticado = usuarioAutenticadoService.get();
        return toResponse(usuarioAutenticado);
    }

    public Optional<Usuario> buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }
}
