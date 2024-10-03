package com.estagioxx.EstagioX.services;

import com.estagioxx.EstagioX.entities.Candidatura;
import com.estagioxx.EstagioX.repositories.CandidaturaRepository;
import com.estagioxx.EstagioX.repositories.OfertaEstagioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidaturaService {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private OfertaEstagioRepository ofertaEstagioRepository;

    public Candidatura save(Candidatura candidatura) {
        return candidaturaRepository.save(candidatura);
    }

    public List<Candidatura> listarCandidaturasPorAluno(Long alunoId) {
        return candidaturaRepository.findByAluno_IdAluno(alunoId); // Certifique-se de usar o nome correto
    }

    public List<Candidatura> listarCandidaturasPorOferta(Long ofertaId) {
        return candidaturaRepository.findByOfertaEstagio_IdOfertaEstagio(ofertaId);
    }


    public void delete(Long idCandidatura) {
        candidaturaRepository.deleteById(idCandidatura);
    }
}
