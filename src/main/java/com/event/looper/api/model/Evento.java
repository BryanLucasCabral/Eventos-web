package com.event.looper.api.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.event.looper.api.dto.EventoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "EVT_ORGANIZADORES_ID", referencedColumnName = "id")
    private Organizador organizador;

    @ManyToMany
    @JoinTable( 
        name = "EVT_EVENTOS_PARTICIPANTES",
        joinColumns = @JoinColumn(name = "evento_id"),
        inverseJoinColumns = @JoinColumn(name = "participante_id")
    )
    private List<Participante> participantes = new ArrayList<>();

    @Min(1)
    @Column(nullable = false)
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
