package com.event.looper.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.event.looper.api.model.Participante;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long>{

    Optional<Participante> findByCpf (String cpf);
}
