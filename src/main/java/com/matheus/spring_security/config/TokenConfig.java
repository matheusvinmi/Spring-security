package com.matheus.spring_security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.matheus.spring_security.model.Usuario;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
public class TokenConfig {

    private String secret = "secret";

    Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken(Usuario usuario){
        return JWT.create()
                .withClaim("usuarioId", usuario.getUsuarioId())
                .withSubject(usuario.getUsuarioEmail())
                .withExpiresAt(Instant.now().plusSeconds(86400000))
                .withIssuedAt(Instant.now())
                .sign(algorithm);

    }

}
