package com.matheus.spring_security.controller;

import com.matheus.spring_security.dto.request.LoginRequestDTO;
import com.matheus.spring_security.dto.request.UsuarioRequestDTO;
import com.matheus.spring_security.dto.response.LoginResponseDTO;
import com.matheus.spring_security.dto.response.UsuarioResponseDTO;
import com.matheus.spring_security.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarTodosUsuario());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPeloId(@Valid @PathVariable Long usuarioId){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(usuarioId));
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.criarUsuario(usuarioRequestDTO);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<UsuarioResponseDTO> criarAdministrador(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.criarAdmin(usuarioRequestDTO);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @PostMapping("atualizar/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@Valid @PathVariable Long usuarioId, @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.atualizarUsuario(usuarioId, usuarioRequestDTO);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @DeleteMapping("deletar/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long usuarioId){
        usuarioService.deletarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        LoginResponseDTO loginResponseDTO = usuarioService.loginUsuario(loginRequestDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }

}
