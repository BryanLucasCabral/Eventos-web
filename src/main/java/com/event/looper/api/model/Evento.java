package com.event.looper.api.model;

import java.time.LocalDate;

import com.event.looper.api.dto.EventoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "EVT_EVENTOS")
public class Evento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String detalhes;

    @Column(nullable = false, unique = true)
    private String slug;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataEvento;

    @ManyToOne
    @JoinColumn(name = "EVT_ORGANIZADORES_ID", referencedColumnName = "id")
    private Organizador organizadorId;

    @Column(nullable = false, name = "LIMITE_PARTICIPANTES")
    private Integer limiteParticipantes;

    public EventoDTO toDto(){
        EventoDTO dto = new EventoDTO();

        dto.setTitulo(titulo);
        dto.setDetalhes(detalhes);
        dto.setSlug(slug);
        dto.setDataEvento(dataEvento);
        dto.setLimiteParticipantes(limiteParticipantes);

        return dto;
    }
}
