package br.com.cwi.redesocial.controller.response.login;

public record LoginResponse(String accessToken, Long expiresIn) {
}
