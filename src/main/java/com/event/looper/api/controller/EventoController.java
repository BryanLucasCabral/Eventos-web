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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.event.looper.api.dto.EventoDTO;
import com.event.looper.api.model.Evento;
import com.event.looper.api.service.EventoService;

@RestController
@RequestMapping("/eventos")
@Secured({"ROLE_ADMIN", "ROLE_ORG", "ROLE_PART"})
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @Secured({"ROLE_ADMIN", "ROLE_ORG"})
    @PostMapping
    public ResponseEntity<Evento> cadastrarEvento(@RequestBody Evento evento){ 
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.cadastrarEvento(evento));
    }

    @GetMapping
    public ResponseEntity<Page<EventoDTO>> listarEventos(@PageableDefault(size = 10, sort = "titulo")Pageable paginacao){
        return ResponseEntity.status(HttpStatus.OK).body(eventoService.listarEventos(paginacao));
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Evento> buscarEventoPeloTitulo(@RequestParam("titulo") String titulo){ 
        return ResponseEntity.status(HttpStatus.OK).body(eventoService.buscarEventoPeloTitulo(titulo));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscarEventoPeloId(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(eventoService.buscarEventoPeloId(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_ORG"})
    @PutMapping("/{id}")
    public ResponseEntity<Evento> atualizarEvento(@PathVariable("id") Long id, Evento dadosEvento){
        Evento evento = eventoService.buscarEventoPeloId(id);

        if (Objects.isNull(evento)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(eventoService.atualizarEvento(id, dadosEvento));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEvento(@PathVariable("id") Long id){
        Evento evento = eventoService.buscarEventoPeloId(id);

        if (Objects.isNull(evento)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        eventoService.deletarEvento(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
