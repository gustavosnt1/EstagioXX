package com.estagioxx.EstagioX.services;


import com.estagioxx.EstagioX.entities.*;
import com.estagioxx.EstagioX.repositories.CandidaturaRepository;
import com.estagioxx.EstagioX.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private EstagioService estagioService;

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Empresa findById(Long idEmpresa) {
        Empresa empresa = null;
        Optional<Empresa> empresax = empresaRepository.findById(idEmpresa);
        if (empresax.isPresent()) {
            empresa = empresax.get();
        }
        return empresa;
    }

    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public boolean authenticate(String email, String password) {
        Optional<Empresa> empresaOptional = empresaRepository.findByEmail(email);


        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            return empresa.getPassword().equals(password);
        }

        return false;
    }

    public Empresa findByEmail(String email) {
        return empresaRepository.findByEmail(email).orElse(null);
    }

    public List<Candidatura> listarCandidatosPorOferta(Long ofertaId) {
        return candidaturaRepository.findByOfertaEstagio_IdOfertaEstagio(ofertaId);
    }


    public void update(Long id, Empresa empresaAtualizada) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(id);

        if (empresaOptional.isPresent()) {
            Empresa empresaExistente = empresaOptional.get();

            empresaExistente.setNome(empresaAtualizada.getNome());
            empresaExistente.setCnpj(empresaAtualizada.getCnpj());
            empresaExistente.setEmail(empresaAtualizada.getEmail());
            empresaExistente.setTelefone(empresaAtualizada.getTelefone());
            empresaExistente.setEndereco(empresaAtualizada.getEndereco());
            empresaExistente.setPessoaContato(empresaAtualizada.getPessoaContato());
            empresaExistente.setAtividadePrincipal(empresaAtualizada.getAtividadePrincipal());
            empresaExistente.setUrlEmpresa(empresaAtualizada.getUrlEmpresa());

            if (empresaAtualizada.getPdfEmpresa() != null) {
                empresaExistente.setPdfEmpresa(empresaAtualizada.getPdfEmpresa());
            }

            empresaRepository.save(empresaExistente);
        } else {
            throw new RuntimeException("Empresa não encontrada para o ID: " + id);
        }
    }
}

