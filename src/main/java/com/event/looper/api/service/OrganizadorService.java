package com.event.looper.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.event.looper.api.dto.OrganizadorDTO;
import com.event.looper.api.exception.CnpjJaCadastradoException;
import com.event.looper.api.exception.EmailJaCadastradoException;
import com.event.looper.api.exception.NaoEncontradoException;
import com.event.looper.api.model.Organizador;
import com.event.looper.api.model.Usuario;
import com.event.looper.api.repository.OrganizadorRepository;
import com.event.looper.api.repository.UsuarioRepository;

@Service
public class OrganizadorService {
    @Autowired
    private OrganizadorRepository organizadorRepository;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Organizador cadastrarOrganizador(Organizador organizador){
        String senha = organizador.getUsuario().getSenha();

        BCryptPasswordEncoder encoder = autenticacaoService.getPasswordEncoder();
        
        String senhaCripitografada = encoder.encode(senha);

        organizador.getUsuario().setSenha(senhaCripitografada);

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(organizador.getUsuario().getEmail());

        if (usuarioOpt.isPresent()) {
            throw new EmailJaCadastradoException("Esse email já está cadastrado!");
        }

        Optional<Organizador> organizadorOpt = organizadorRepository.findByCnpj(organizador.getCnpj());

        if (organizadorOpt.isPresent()) {
            throw new CnpjJaCadastradoException("Esse Cnpj já está cadastrado!");
        }

        Usuario usuario = usuarioRepository.save(organizador.getUsuario());
        organizador.setUsuario(usuario);

        return organizadorRepository.save(organizador);
    }

    public Page<OrganizadorDTO> listarOrganizadores(Pageable paginacao){
        return organizadorRepository.findAll(paginacao).map(organizador -> organizador.toDto());
    }

    public Organizador buscarOrganizadorPeloId(Long id){ 
        Optional<Organizador> organizadorOpt = organizadorRepository.findById(id);

        if (organizadorOpt.isPresent()) {
            return organizadorOpt.get();
        }
        return null;
    }

    public Organizador buscarOrganizadoPeloCnpj(String cnpj){
        Optional<Organizador> organizadorOpt = organizadorRepository.findByCnpj(cnpj);

        if (organizadorOpt.isPresent()) {
            return organizadorOpt.get();
        }
        return null;
    }

    public Organizador atualizarOrganizador(Long id, Organizador dadosOrganizador){
        Optional<Organizador> organizadorOpt = organizadorRepository.findById(id);

        if (organizadorOpt.isEmpty()) {
            throw new NaoEncontradoException("Organizador não foi encontrado!");
        }

        Organizador organizadorExistente = organizadorOpt.get();

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(dadosOrganizador.getUsuario().getEmail());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(organizadorExistente.getUsuario().getId())) {
            throw new EmailJaCadastradoException("Esse email já está cadastrado!");
        }

        organizadorExistente.setCnpj(dadosOrganizador.getCnpj());
        organizadorExistente.setRazaoSocial(dadosOrganizador.getRazaoSocial());

        Usuario usuarioExistenteOrganizador = organizadorExistente.getUsuario();
        usuarioExistenteOrganizador.setEmail(dadosOrganizador.getUsuario().getEmail());

        if (!dadosOrganizador.getUsuario().getSenha().isEmpty()) {
            BCryptPasswordEncoder encoder = autenticacaoService.getPasswordEncoder();
            String senhaCripitografada = encoder.encode(dadosOrganizador.getUsuario().getSenha());
            usuarioExistenteOrganizador.setSenha(senhaCripitografada);
        }

        usuarioRepository.save(usuarioExistenteOrganizador);

        return organizadorRepository.save(organizadorExistente);
    }

    public void deletarOrganizador(Long id){ 
        Optional<Organizador> organizadorOpt = organizadorRepository.findById(id);

        if (organizadorOpt.isPresent()) {
             organizadorRepository.deleteById(id);
        }
        
    }
}
