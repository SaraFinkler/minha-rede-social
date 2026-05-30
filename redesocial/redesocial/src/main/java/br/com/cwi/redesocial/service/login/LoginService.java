package br.com.cwi.redesocial.service.login;

import br.com.cwi.redesocial.controller.response.login.LoginResponse;
import br.com.cwi.redesocial.controller.response.usuario.UsuarioResponse;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.service.usuario.BuscarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LoginService {

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtEncoder jwtEncoder;

    public ResponseEntity<LoginResponse> login(String email, String senha) {

        UsuarioResponse response = buscarUsuarioService.buscarPorEmail(email);

        Usuario usuario = buscarUsuarioService.porId(response.getId());

        if(!isLoginCorreto(senha, usuario.getSenha())){
            throw new BadCredentialsException("Usuário ou senha incorretos!");
        }

        long expiresIn = 12000L;

        JwtClaimsSet jwt = JwtClaimsSet.builder()
                .issuer("seguranca-api")
                .subject(usuario.getNomeCompleto())
                .expiresAt(Instant.now().plusSeconds(expiresIn))
                .issuedAt(Instant.now())
                .claim("email", usuario.getEmail())
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(jwt)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(token, expiresIn));
    }

    private boolean isLoginCorreto(String password, String savedPassword){
        return passwordEncoder.matches(password, savedPassword);
    }
}