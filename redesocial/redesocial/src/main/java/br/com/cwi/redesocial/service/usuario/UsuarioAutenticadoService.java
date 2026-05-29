package br.com.cwi.redesocial.service.usuario;

import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class UsuarioAutenticadoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "Usuário não está autenticado");
        }

        Object credentials = authentication.getCredentials();
        if (!(credentials instanceof Jwt)) {
            throw new ResponseStatusException(UNAUTHORIZED, "Token JWT inválido");
        }

        Jwt jwt = (Jwt) credentials;
        Object emailClaim = jwt.getClaim("email");
        if (emailClaim == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "Token JWT inválido");
        }

        return emailClaim.toString();
    }

    public Usuario get() {
        return usuarioRepository.findByEmail(getEmail())
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "Usuário não autenticado"));
    }
}
