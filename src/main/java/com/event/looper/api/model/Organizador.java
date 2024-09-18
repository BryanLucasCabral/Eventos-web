package com.event.looper.api.model;


import com.event.looper.api.dto.OrganizadorDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "EVT_ORGANIZADORES")
public class Organizador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id",nullable = false)
    private Usuario usuario;

    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos numéricos")
    @Column(nullable = false,length = 14, unique = true)
    private String cnpj;

    @Column(nullable = false, unique = true)
    private String razaoSocial;

    public OrganizadorDTO toDto (){
        OrganizadorDTO dto = new OrganizadorDTO();

        dto.setCnpj(cnpj);
        dto.setRazaoSocial(razaoSocial);
        dto.setUsuario(usuario.toDto());

        return dto;
    }
}
