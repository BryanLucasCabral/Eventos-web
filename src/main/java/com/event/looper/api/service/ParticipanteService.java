package com.event.looper.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.event.looper.api.dto.ParticipanteDTO;
import com.event.looper.api.exception.CnpjJaCadastradoException;
import com.event.looper.api.exception.EmailJaCadastradoException;
import com.event.looper.api.exception.NaoEncontradoException;
import com.event.looper.api.model.Participante;
import com.event.looper.api.model.Usuario;
import com.event.looper.api.repository.ParticipanteRepository;
import com.event.looper.api.repository.UsuarioRepository;

@Service
public class ParticipanteService {
   
    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Participante cadastrarParticipante(Participante participante){ 
        String senha = participante.getUsuario().getSenha();

        BCryptPasswordEncoder encoder = autenticacaoService.getPasswordEncoder();

        String senhaEncripitada = encoder.encode(senha);

        participante.getUsuario().setSenha(senhaEncripitada);

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(participante.getUsuario().getEmail());

        if (usuarioOpt.isPresent()) {
            throw new EmailJaCadastradoException("Esse email já está cadastrado!");
        }

        Optional<Participante> participanteOpt = participanteRepository.findByCpf(participante.getCpf());

        if (participanteOpt.isPresent()) {
            throw new CnpjJaCadastradoException("Esse cpf já está cadastrado!");
        }

        Usuario usuario = usuarioRepository.save(participante.getUsuario());
        participante.setUsuario(usuario);

        return participanteRepository.save(participante);
    }

    public Page<ParticipanteDTO> listarParticipantes(Pageable paginacao){
        return participanteRepository.findAll(paginacao).map(participante -> participante.toDTO());
    }

    public Participante buscarParticipantePeloId(Long id){ 
        Optional<Participante> participanteOpt = participanteRepository.findById(id);

        if (participanteOpt.isPresent()) {
            return participanteOpt.get();
        }

        throw new NaoEncontradoException("Participante não foi encontrado!");
    }

    public Participante buscarParticipantePeloCpf(String cpf){
        Optional<Participante> participanteOpt = participanteRepository.findByCpf(cpf);

        if (participanteOpt.isPresent()) {
            return participanteOpt.get();
        }

        throw new NaoEncontradoException("Esse cpf não foi encontrado!");
    }

    public Participante atualizarParticipante(Long id, Participante dadosParticipante){ 
        Optional<Participante> participanteOpt = participanteRepository.findById(id);
        if (participanteOpt.isEmpty()) {
            throw new NaoEncontradoException("Participante não foi encontrado!");
        }

        Participante participanteExistente = participanteOpt.get();

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(participanteExistente.getUsuario().getEmail());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(participanteExistente.getUsuario().getId())) {
            throw new EmailJaCadastradoException("Esse email já está cadastrado!");
        }

        participanteExistente.setCpf(dadosParticipante.getCpf());
        participanteExistente.setEventos(dadosParticipante.getEventos());
        
        Usuario usuarioExistenParticipante = participanteExistente.getUsuario();
        usuarioExistenParticipante.setEmail(dadosParticipante.getUsuario().getEmail());

        if (!dadosParticipante.getUsuario().getSenha().isEmpty()) {
            BCryptPasswordEncoder encoder = autenticacaoService.getPasswordEncoder();
            String senhaCripitografada = encoder.encode(dadosParticipante.getUsuario().getSenha());
            usuarioExistenParticipante.setSenha(senhaCripitografada);
        }

        usuarioRepository.save(usuarioExistenParticipante);

        return participanteRepository.save(participanteExistente);
    }

    public void delatarParticipante(Long id){
        Optional<Participante> participanteOpt = participanteRepository.findById(id);

        if (participanteOpt.isPresent()) {
            participanteRepository.deleteById(id);
        }

        throw new NaoEncontradoException("Participante não foi encontrado!"); 
    }
}
