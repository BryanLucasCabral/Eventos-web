package com.event.looper.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.event.looper.api.dto.EventoDTO;
import com.event.looper.api.exception.NaoEncontradoException;
import com.event.looper.api.model.Evento;
import com.event.looper.api.repository.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public Evento cadastrarEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Page<EventoDTO> listarEventos(Pageable paginacao) {
        return eventoRepository.findAll(paginacao).map(evento -> evento.toDto());
    }

    public List<Evento> buscarEventosPelaData(LocalDate dataEvento) {
        return eventoRepository.findByDataEvento(dataEvento);
    }

    public Evento buscarEventoPeloTitulo(String titulo) {
        Optional<Evento> eventoOpt = eventoRepository.findByTitulo(titulo);

        if (eventoOpt.isPresent()) {
            return eventoOpt.get();
        }

        throw new NaoEncontradoException("Evento não foi encontrado");
    }

    public Evento buscarEventoPeloId(Long id) {
        Optional<Evento> eventoOpt = eventoRepository.findById(id);

        if (eventoOpt.isPresent()) {
            return eventoOpt.get();
        }
        throw new NaoEncontradoException("Evento não foi encontrado");
    }

    public Evento atualizarEvento(Long id, Evento dadosEvento) {
        Optional<Evento> eventoOpt = eventoRepository.findById(id);

        if (eventoOpt.isPresent()) {
            Evento evento = eventoOpt.get();

            evento.setTitulo(dadosEvento.getTitulo());
            evento.setDetalhes(dadosEvento.getDetalhes());
            evento.setSlug(dadosEvento.getSlug());
            evento.setLimiteParticipantes(dadosEvento.getLimiteParticipantes());
            evento.setDataEvento(dadosEvento.getDataEvento());
            evento.setOrganizador(dadosEvento.getOrganizador());

            return eventoRepository.save(evento);
        }

        throw new NaoEncontradoException("Evento não foi encontrado");
    }

    public void deletarEvento(Long id) {
        Optional<Evento> eventoOpt = eventoRepository.findById(id);
        if (eventoOpt.isEmpty()) {
            throw new NaoEncontradoException("Evento não foi encontrado");
        }
        eventoRepository.deleteById(id);
    }
}
