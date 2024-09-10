package com.event.looper.api.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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

import com.event.looper.api.dto.OrganizadorDTO;
import com.event.looper.api.model.Organizador;
import com.event.looper.api.service.OrganizadorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/organizadores")
public class OrganizadorController {
    
    @Autowired
    private OrganizadorService organizadorService;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Organizador> cadastrarOrganizador(@RequestBody Organizador organizador){
        return ResponseEntity.status(HttpStatus.CREATED).body(organizadorService.cadastrarOrganizador(organizador));
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_PART", "ROLE_ORG"})
    @GetMapping
    public ResponseEntity<Page<OrganizadorDTO>> ListarOrganizadores(@PageableDefault(size = 10, sort = "nome", direction = Direction.DESC) Pageable paginacao){
        return ResponseEntity.status(HttpStatus.OK).body(organizadorService.listarOrganizadores(paginacao));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<Organizador> buscarOrganizadorPeloId(@PathVariable("id") Long id){
        Organizador organizador = organizadorService.buscarOrganizadorPeloId(id);

        if (Objects.isNull(organizador)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(organizador);
    }

    @Secured({"ROLE_ADMIN", "ROLE_PART", "ROLE_ORG"})
    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<Organizador> buscarOrganizadorPeloCnpj(@PathVariable("cnpj") String cnpj){ 
        Organizador organizador = organizadorService.buscarOrganizadoPeloCnpj(cnpj);

        if (Objects.isNull(organizador)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(organizador);
    }

    @Secured({"ROLE_ADMIN", "ROLE_ORG"})
    @PutMapping("/{id}")
    public ResponseEntity<Organizador> atualizarOrganizador(@PathVariable("id") Long id, @Valid @RequestBody Organizador dadosOrganizador){
        Organizador organizador = organizadorService.buscarOrganizadorPeloId(id);

        if ( Objects.isNull(organizador)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(organizadorService.atualizarOrganizador(id, dadosOrganizador));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOrganizador (@PathVariable("id") Long id){
        Organizador organizador = organizadorService.buscarOrganizadorPeloId(id);

        if (Objects.isNull(organizador)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        organizadorService.deletarOrganizador(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
