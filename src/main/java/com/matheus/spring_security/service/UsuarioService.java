package com.matheus.spring_security.service;

import com.matheus.spring_security.dto.request.UsuarioRequestDTO;
import com.matheus.spring_security.dto.response.UsuarioResponseDTO;
import com.matheus.spring_security.model.Usuario;
import com.matheus.spring_security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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
        String senhaCriptografada = encoder.encode(usuarioRequestDTO.usuarioSenha());
        usuario.setUsuarioSenha(senhaCriptografada);
        Usuario salvo = usuarioRepository.save(usuario);
        return toResponse(salvo);
    }

    @Transactional
    public UsuarioResponseDTO atualizarUsuario(Long usuarioId, UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Não tem nenhum usuario com esse ID para fazer a atualização"));
        usuario.setUsuarioNome(usuarioRequestDTO.usuarioNome());
        usuario.setUsuarioEmail(usuarioRequestDTO.usuarioEmail());
        String senhaCriptografada = encoder.encode(usuarioRequestDTO.usuarioSenha());
        usuario.setUsuarioSenha(senhaCriptografada);
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


    public UsuarioResponseDTO toResponse(Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.getUsuarioId(),
                usuario.getUsuarioNome(),
                usuario.getUsuarioEmail(),
                usuario.getUsuarioSenha()
        );
    }

}
