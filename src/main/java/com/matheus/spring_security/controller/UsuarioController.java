package com.matheus.spring_security.controller;

import com.matheus.spring_security.dto.request.LoginRequestDTO;
import com.matheus.spring_security.dto.request.UsuarioRequestDTO;
import com.matheus.spring_security.dto.response.LoginResponseDTO;
import com.matheus.spring_security.dto.response.UsuarioResponseDTO;
import com.matheus.spring_security.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(
        name = "Autenticação e Usuários",
        description = "Endpoints para autenticação, cadastro e gerenciamento de usuários"
)
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Listar usuários",
            description = "Retorna uma lista com todos os usuários cadastrados. Apenas ADMIN pode acessar."
    )
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarTodosUsuario());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário específico pelo ID. Apenas ADMIN pode acessar."
    )
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPeloId(@Valid @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarUsuarioPorId(id));
    }

    @PostMapping("/cadastro-usuario")
    @Operation(
            summary = "Cadastrar usuário",
            description = "Cria um novo usuário com perfil padrão USER."
    )
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.criarUsuario(usuarioRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponseDTO);
    }

    @PostMapping("/cadastro-admin")
    @Operation(
            summary = "Cadastrar administrador",
            description = "Cria um novo usuário com perfil ADMIN."
    )
    public ResponseEntity<UsuarioResponseDTO> criarAdministrador(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.criarAdmin(usuarioRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN') or #id == authentication.principal.usuarioId")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário. Permitido para o próprio usuário ou ADMIN."
    )
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@Valid @PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.atualizarUsuario(id, usuarioRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioResponseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.usuarioId")
    @Operation(
            summary = "Deletar usuário",
            description = "Remove um usuário do sistema. Permitido para o próprio usuário ou ADMIN."
    )
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/login")
    @Operation(
            summary = "Login",
            description = "Autentica o usuário e retorna token de acesso."
    )
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        LoginResponseDTO loginResponseDTO = usuarioService.loginUsuario(loginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
    }

}
