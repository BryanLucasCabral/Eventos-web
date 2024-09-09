package com.event.looper.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.event.looper.api.dto.AutenticacaoDTO;
import com.event.looper.api.dto.UsuarioDTO;
import com.event.looper.api.model.Usuario;
import com.event.looper.api.service.TokenService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody AutenticacaoDTO dto){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        Usuario usuario = (Usuario) authentication.getPrincipal();

        String token = tokenService.gerarToken(usuario);

        UsuarioDTO usuarioDTO = usuario.toDto();
        usuarioDTO.setToken(token);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioDTO);
    }
}
