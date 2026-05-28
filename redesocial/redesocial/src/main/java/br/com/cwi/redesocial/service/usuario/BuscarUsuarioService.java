package br.com.cwi.redesocial.service.usuario;

import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.mapper.usuario.UsuarioMapper;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class BuscarUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario porId(long id) {
        return usuarioRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
    }

    public UsuarioResponse buscarPorEmail(String email){
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if(usuario.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuario não encontrado"
            );
        }

        return UsuarioMapper.toResponse(usuario.get());
    }
}
