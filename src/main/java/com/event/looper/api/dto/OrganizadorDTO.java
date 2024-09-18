package com.event.looper.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizadorDTO {
    private String cnpj;
    private String razaoSocial;
    private UsuarioDTO usuario;
}
