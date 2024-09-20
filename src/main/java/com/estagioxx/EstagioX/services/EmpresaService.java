package com.estagioxx.EstagioX.services;


import com.estagioxx.EstagioX.entities.Candidatura;
import com.estagioxx.EstagioX.entities.Empresa;
import com.estagioxx.EstagioX.entities.Estagio;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
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

    @Autowired
    private OfertaEstagioService ofertaEstagioService;

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

    public boolean authenticate(String email) {
        Optional<Empresa> empresaOptional = empresaRepository.findByEmail(email);


        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            return empresa.getEmail().equals(email);
        }

        return false;
    }

    public Empresa findByEmail(String email) {
        return empresaRepository.findByEmail(email).orElse(null);
    }

    public List<Candidatura> listarCandidatosPorOferta(Long ofertaId) {
        return candidaturaRepository.findByOfertaEstagio_IdOfertaEstagio(ofertaId);
    }

    public void aprovarCandidato(Long candidaturaId) {
        Candidatura candidatura = candidaturaRepository.findById(candidaturaId)
                .orElseThrow(() -> new RuntimeException("Candidatura não encontrada"));

        candidatura.setStatus(Candidatura.StatusCandidatura.ACEITA);
        candidaturaRepository.save(candidatura);

        OfertaEstagio ofertaEstagio = candidatura.getOfertaEstagio();

        if (estagioService.existsByOfertaEstagio(ofertaEstagio.getIdOfertaEstagio())) {
            return;
        }

        Estagio estagio = new Estagio();
        estagio.setOfertaEstagio(ofertaEstagio);
        estagio.setAluno(candidatura.getAluno());
        estagioService.save(estagio);


        ofertaEstagio.setPreenchida(true);
        ofertaEstagioService.save(ofertaEstagio);
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

