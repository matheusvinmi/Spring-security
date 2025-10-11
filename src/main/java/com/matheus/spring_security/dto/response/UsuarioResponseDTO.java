package com.matheus.spring_security.dto.response;

import com.matheus.spring_security.model.Role;

import java.util.Set;

public record UsuarioResponseDTO(Long usuarioId, String usuarioNome, String usuarioEmail, String usuarioSenha, Set<Role> role) {}
