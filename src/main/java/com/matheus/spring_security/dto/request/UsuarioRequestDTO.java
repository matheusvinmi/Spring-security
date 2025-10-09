package com.matheus.spring_security.dto.request;

import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @NotNull(message = "O nome é obrigatorio") String usuarioNome,
        @NotNull(message = "O nome é obrigatorio") String usuarioEmail,
        @NotNull(message = "O nome é obrigatorio") String usuarioSenha
) {}
