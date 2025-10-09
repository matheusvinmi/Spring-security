package com.matheus.spring_security.dto.response;

public record LoginResponseDTO(String usuarioNome, String usuarioEmail, String usuarioSenha, String token) {
}
