package com.event.looper.api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.event.looper.api.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long>{

    Optional<Evento> findByTitulo(String titulo);

    List<Evento> findByDataEvento(LocalDate dataEvento);
}
