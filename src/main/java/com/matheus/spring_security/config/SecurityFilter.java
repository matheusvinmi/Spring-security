package com.matheus.spring_security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.rmi.ServerError;
import java.util.List;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenConfig tokenConfig;

    public SecurityFilter(TokenConfig tokenConfig){
        this.tokenConfig = tokenConfig;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (Strings.isNotEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer")){
            String token = authorizationHeader.substring("Bearer".length());
            Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
            if (optUser.isPresent()){
                JWTUserData userData = optUser.get();

                List<SimpleGrantedAuthority> authorities = userData.roles().stream()
                        .map(SimpleGrantedAuthority::new).toList();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userData, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

}
