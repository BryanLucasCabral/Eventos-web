package com.event.looper.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.event.looper.api.model.Organizador;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador,Long> {

    Optional<Organizador> findByCnpj(String cnpj);

    Optional<Organizador> findByUsuario_Email(String email);

    List<Organizador> findByRazaoSocialContainsIgnoreCase(String razaoSocial);

    List<Organizador> findByUsuario_NomeContainsIgnoreCase(String nome);
}