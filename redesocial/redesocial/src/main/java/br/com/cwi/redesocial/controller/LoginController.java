package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.controller.request.login.LoginRequest;
import br.com.cwi.redesocial.controller.response.login.LoginResponse;
import br.com.cwi.redesocial.domain.Permissao;
import br.com.cwi.redesocial.domain.Usuario;
import br.com.cwi.redesocial.security.service.BuscarUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final BuscarUsuarioService buscarUsuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public LoginController(BuscarUsuarioService buscarUsuarioService, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.buscarUsuarioService = buscarUsuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }



    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        Optional<Usuario> optUser = buscarUsuarioService.buscarPorEmail(loginRequest.getEmail());

        String teste = passwordEncoder.encode(loginRequest.getSenha());

        if(optUser.isEmpty() || !isLoginCorreto(loginRequest.getSenha(), optUser.get().getSenha())){
            throw new BadCredentialsException("Usuário ou senha incorretos!");
        }

        Usuario usuario = optUser.get();
        List<String> permissoes = usuario.getPermissoes().stream()
                .map(Permissao::getNome)
                .toList();
        long expiresIn = 600L;

        JwtClaimsSet jwt = JwtClaimsSet.builder()
                .issuer("seguranca-api")
                .subject(usuario.getNomeCompleto())
                .expiresAt(Instant.now().plusSeconds(expiresIn))
                .issuedAt(Instant.now())
                .claim("email", usuario.getEmail())
                .claim("scope", permissoes)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(jwt)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(token, expiresIn));
    }

    private boolean isLoginCorreto(String password, String savedPassword){
        return passwordEncoder.matches(password, savedPassword);
    }
}
