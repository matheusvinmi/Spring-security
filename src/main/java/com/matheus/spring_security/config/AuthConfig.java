package com.matheus.spring_security.config;

import com.matheus.spring_security.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthConfig implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AuthConfig(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return usuarioRepository.findByUsuarioEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + " não encontrado"));
    }

}
