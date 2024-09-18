package com.event.looper.api.dto;

import java.time.LocalDate;

import com.event.looper.api.model.Endereco;
import com.event.looper.api.model.Organizador;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {
    private String titulo;
    private String detalhes;
    private String slug;
    private LocalDate dataEvento;
    private Integer limiteParticipantes;
    private Organizador organizadorId;
    private Endereco endereco;
}
