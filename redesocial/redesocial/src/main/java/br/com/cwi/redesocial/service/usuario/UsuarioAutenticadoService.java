package br.com.cwi.redesocial.service.usuario;

import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class UsuarioAutenticadoService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioAutenticadoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Usuário não está autenticado");
        }

        Object credentials = authentication.getCredentials();
        if (!(credentials instanceof Jwt)) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Token JWT não encontrado nas credenciais");
        }

        Jwt jwt = (Jwt) credentials;
        Object emailClaim = jwt.getClaim("email");
        if (emailClaim == null) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Claim 'email' não encontrado no token JWT");
        }

        return emailClaim.toString();
    }

    public Usuario get() {
        return usuarioRepository.findByEmail(getEmail())
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "Usuário não existe ou não esta autenticado"));
    }
}
