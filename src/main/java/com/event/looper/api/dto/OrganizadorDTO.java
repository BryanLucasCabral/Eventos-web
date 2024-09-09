package com.event.looper.api.dto;

import com.event.looper.api.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizadorDTO {
    private String cnpj;
    private String razaoSocial;
    private Usuario usuario;
}
