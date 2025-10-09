package com.matheus.spring_security.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @NotNull(message = "O email é obrigatório para o login!") String usuarioEmail,
        @NotNull(message = "A senha é obrigatório para o login!") String usuarioSenha
) {
}
