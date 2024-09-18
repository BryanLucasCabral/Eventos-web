package com.event.looper.api.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.event.looper.api.dto.ParticipanteDTO;
import com.event.looper.api.model.Participante;
import com.event.looper.api.service.ParticipanteService;

@RestController
@RequestMapping("/participantes")
@Secured({"ROLE_ADMIN", "ROLE_PART"})
public class ParticipanteController {
    
    @Autowired
    private ParticipanteService participanteService;

    @Secured({"ROLE_ADMIN", "ROLE_PART"})
    @PostMapping
    public ResponseEntity<Participante> cadastrarParticipante(@RequestBody Participante participante){
        return ResponseEntity.status(HttpStatus.CREATED).body(participanteService.cadastrarParticipante(participante));
    }
    
    @GetMapping
    public ResponseEntity<Page<ParticipanteDTO>> listarParticipantes(@PageableDefault(size = 10, sort = "nome") Pageable paginacao){
        return ResponseEntity.status(HttpStatus.OK).body(participanteService.listarParticipantes(paginacao));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<Participante> buscarParticipantePeloId (@PathVariable ("id")Long id){ 
        Participante participante = participanteService.buscarParticipantePeloId(id);

        if (Objects.isNull(participante)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(participante);
    }
    
    @GetMapping("/busca/{cpf}")
    public ResponseEntity<Participante> buscarParticipantePeloId(@PathVariable("cpf") String cpf){
        Participante participante = participanteService.buscarParticipantePeloCpf(cpf);

        if (Objects.isNull(participante)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(participante);
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<Participante> atualizarParticipante(@PathVariable("id") Long id, Participante dadosParticipante){ 
        Participante participante = participanteService.buscarParticipantePeloId(id);

        if (Objects.isNull(participante)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(participanteService.atualizarParticipante(id, dadosParticipante));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarParticipante(@PathVariable("id") Long id) {
        Participante participante = participanteService.buscarParticipantePeloId(id);

        if (Objects.isNull(participante)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        participanteService.delatarParticipante(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
