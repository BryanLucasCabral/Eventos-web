package com.event.looper.api.model;


import com.event.looper.api.dto.OrganizadorDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    @OneToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @Column(nullable = false,length = 14, unique = true)
    private String cnpj;

    @Column(nullable = false, unique = true)
    private String razaoSocial;

    public OrganizadorDTO toDto (){
        OrganizadorDTO dto = new OrganizadorDTO();

        dto.setCnpj(cnpj);
        dto.setRazaoSocial(razaoSocial);
        dto.setUsuario(usuario);

        return dto;
    }
}
