package com.matheus.spring_security.service;

import com.matheus.spring_security.config.TokenConfig;
import com.matheus.spring_security.dto.request.LoginRequestDTO;
import com.matheus.spring_security.dto.request.UsuarioRequestDTO;
import com.matheus.spring_security.dto.response.LoginResponseDTO;
import com.matheus.spring_security.dto.response.UsuarioResponseDTO;
import com.matheus.spring_security.model.Role;
import com.matheus.spring_security.model.Usuario;
import com.matheus.spring_security.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenConfig tokenConfig;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager, TokenConfig tokenConfig){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }


    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodosUsuario(){
        return usuarioRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarUsuarioPorId(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Não tem nenhum usuario com esse ID"));
        return toResponse(usuario);
    }

    @Transactional
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuario = new Usuario();
        usuario.setUsuarioNome(usuarioRequestDTO.usuarioNome());
        usuario.setUsuarioEmail(usuarioRequestDTO.usuarioEmail());
        String senhaCriptografada = passwordEncoder.encode(usuarioRequestDTO.usuarioSenha());
        usuario.setUsuarioSenha(senhaCriptografada);
        usuario.setRoles(Set.of(Role.ROLE_USER));
        Usuario salvo = usuarioRepository.save(usuario);
        return toResponse(salvo);
    }

    @Transactional
    public UsuarioResponseDTO criarAdmin(UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuario = new Usuario();
        usuario.setUsuarioNome(usuarioRequestDTO.usuarioNome());
        usuario.setUsuarioEmail(usuarioRequestDTO.usuarioEmail());
        String senhaCriptografada = passwordEncoder.encode(usuarioRequestDTO.usuarioSenha());
        usuario.setUsuarioSenha(senhaCriptografada);
        usuario.setRoles(Set.of(Role.ROLE_ADMIN));
        Usuario salvo = usuarioRepository.save(usuario);
        return toResponse(salvo);
    }

    @Transactional
    public UsuarioResponseDTO atualizarUsuario(Long usuarioId, UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Não tem nenhum usuario com esse ID para fazer a atualização"));
        usuario.setUsuarioNome(usuarioRequestDTO.usuarioNome());
        usuario.setUsuarioEmail(usuarioRequestDTO.usuarioEmail());
        String senhaCriptografada = passwordEncoder.encode(usuarioRequestDTO.usuarioSenha());
        usuario.setUsuarioSenha(senhaCriptografada);
        Usuario salvo = usuarioRepository.save(usuario);
        return toResponse(salvo);
    }

    @Transactional
    public void deletarUsuario(Long usuarioId){
        if(usuarioRepository.existsById(usuarioId)){
            usuarioRepository.deleteById(usuarioId);
        }else{
            System.out.println("O ID informado não existe");
        }
    }

    @Transactional
    public LoginResponseDTO loginUsuario(LoginRequestDTO loginRequestDTO){
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(loginRequestDTO.usuarioEmail(), loginRequestDTO.usuarioSenha());
        Authentication authentication = authenticationManager.authenticate(userAndPass);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenConfig.generateToken(usuario);

        return new LoginResponseDTO(usuario.getUsuarioNome(), usuario.getUsuarioEmail(), usuario.getUsuarioSenha(), token, usuario.getRoles());
    }


    public UsuarioResponseDTO toResponse(Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.getUsuarioId(),
                usuario.getUsuarioNome(),
                usuario.getUsuarioEmail(),
                usuario.getUsuarioSenha(),
                usuario.getRoles()
        );
    }

    public LoginResponseDTO toResponseLogin(Usuario login){
        return new LoginResponseDTO(
                login.getUsuarioNome(),
                login.getUsername(),
                login.getPassword(),
                null,
                login.getRoles()
        );
    }

}
