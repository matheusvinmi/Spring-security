package com.matheus.spring_security.dto.response;

import com.matheus.spring_security.model.Role;

import java.util.Set;

public record LoginResponseDTO(String usuarioNome, String usuarioEmail, String usuarioSenha, String token, Set<Role> role) {
}
