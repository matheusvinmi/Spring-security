package com.matheus.spring_security.config;

import lombok.Builder;

import java.util.List;

@Builder
public record JWTUserData(Long usuarioId, String usuarioEmail, List<String> roles) {

}
