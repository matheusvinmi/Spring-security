package com.matheus.spring_security.controller;

import com.matheus.spring_security.dto.request.UsuarioRequestDTO;
import com.matheus.spring_security.dto.response.UsuarioResponseDTO;
import com.matheus.spring_security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarTodosUsuario());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPeloId(@PathVariable Long usuarioId){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.criarUsuario(usuarioRequestDTO);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @PostMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long usuarioId, @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.atualizarUsuario(usuarioId, usuarioRequestDTO);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long usuarioId){
        usuarioService.deletarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }

}
