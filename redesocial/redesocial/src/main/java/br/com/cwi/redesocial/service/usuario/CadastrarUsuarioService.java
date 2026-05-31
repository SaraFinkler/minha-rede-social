package br.com.cwi.redesocial.service.usuario;

import br.com.cwi.redesocial.controller.request.usuario.UsuarioRequest;
import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.usuario.UsuarioMapper;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class CadastrarUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CadastrarUsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse cadastrar(UsuarioRequest request){

        usuarioRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new ResponseStatusException(CONFLICT, "Já existe um usuário cadastrado com o email informado.");
        });

        Usuario usuario = UsuarioMapper.toEntity(request);
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setAtivo(true);

        usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }
}
