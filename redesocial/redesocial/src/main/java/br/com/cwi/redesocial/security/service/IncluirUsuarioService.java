package br.com.cwi.redesocial.security.service;

import br.com.cwi.redesocial.controller.request.usuario.UsuarioRequest;
import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.usuario.UsuarioMapper;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IncluirUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public IncluirUsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse incluir(UsuarioRequest request){
        Usuario usuario = UsuarioMapper.toEntity(request);
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setAtivo(true);

        usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }
}
