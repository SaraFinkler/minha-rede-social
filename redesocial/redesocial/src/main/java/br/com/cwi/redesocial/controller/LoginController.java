package br.com.cwi.redesocial.controller;

import br.com.cwi.redesocial.controller.request.login.LoginRequest;
import br.com.cwi.redesocial.controller.response.login.LoginResponse;
import br.com.cwi.redesocial.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return  loginService.login(loginRequest.getEmail(), loginRequest.getSenha());
    }
}
