package com.event.looper.api.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.event.looper.api.dto.UsuarioDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity(name = "EVT_USUARIOS")
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String celular;

    @Column(nullable = false)
    private Boolean organizador;

    @Column(nullable = false)
    private Boolean participante;

    @Column(nullable = false)
    private Boolean administrador;

    public Usuario(){ 
        this.organizador = Boolean.FALSE;
        this.participante = Boolean.TRUE;
        this.administrador = Boolean.FALSE;
    }

    public UsuarioDTO toDto (){
        UsuarioDTO dto = new UsuarioDTO();
            dto.setNome(nome);
            dto.setEmail(email);
            dto.setCelular(celular);
            dto.setOrganizador(organizador);
            dto.setParticipante(participante);
            dto.setToken(null);

            return dto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (organizador) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ORG"));
        }
        if (participante) {
            authorities.add(new SimpleGrantedAuthority("ROLE_PART"));
        }
        if (administrador) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }

}
