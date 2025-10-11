package com.matheus.spring_security.dto.request;

import com.matheus.spring_security.model.Role;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @NotNull(message = "O nome é obrigatorio") String usuarioNome,
        @NotNull(message = "O nome é obrigatorio") String usuarioEmail,
        @NotNull(message = "O nome é obrigatorio") String usuarioSenha,
        Role role
) {}
