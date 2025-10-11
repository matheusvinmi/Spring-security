package com.matheus.spring_security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.matheus.spring_security.model.Usuario;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;


@Component
public class TokenConfig {

    private String secret = "secret";

    Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken(Usuario usuario){
        return JWT.create()
                .withClaim("usuarioId", usuario.getUsuarioId())
                .withSubject(usuario.getUsuarioEmail())
                .withClaim("roles", usuario.getRoles().stream().map(Enum::name).toList())
                .withExpiresAt(Instant.now().plusSeconds(86400000))
                .withIssuedAt(Instant.now())
                .sign(algorithm);

    }

    public Optional<JWTUserData> validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decode = JWT.require(algorithm)
                    .build().verify(token);

            return Optional.of(JWTUserData.builder()
                    .usuarioId(decode.getClaim("usuarioId").asLong())
                    .usuarioEmail(decode.getSubject())
                            .roles(decode.getClaim("roles").asList(String.class))
                    .build());
        }catch (JWTCreationException e){
            return Optional.empty();
        }
    }

}
