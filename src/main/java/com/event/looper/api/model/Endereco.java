package com.event.looper.api.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Endereco {

    private String cep;
    private String loogradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String estado;
    private String numero;
}
