package com.estagioxx.EstagioX.services;

import com.estagioxx.EstagioX.entities.Candidatura;
import com.estagioxx.EstagioX.entities.Empresa;
import com.estagioxx.EstagioX.entities.OfertaEstagio;

import com.estagioxx.EstagioX.repositories.OfertaEstagioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfertaEstagioService {

    @Autowired
    private OfertaEstagioRepository ofertaEstagioRepository;


    @Autowired
    private CandidaturaService candidaturaService;

    public List<OfertaEstagio> findAll() {
        return ofertaEstagioRepository.findAll();
    }

    public OfertaEstagio findById(Long idOfertaEstagio) {
        OfertaEstagio oferta = null;
        Optional<OfertaEstagio> ofertax = ofertaEstagioRepository.findById(idOfertaEstagio);
        if (ofertax.isPresent()) {
            oferta = ofertax.get();
        }
        return oferta;
    }

    public OfertaEstagio save(OfertaEstagio ofertaEstagio) {
        return ofertaEstagioRepository.save(ofertaEstagio);
    }

    public List<OfertaEstagio> findByEmpresa(Empresa empresa) {
        return ofertaEstagioRepository.findByEmpresas(empresa);
    }

    public Page<OfertaEstagio> findByEmpresaWithPagination(Empresa empresa, Pageable pageable){
        return ofertaEstagioRepository.findByEmpresas(empresa, pageable);
    }


    public Page<OfertaEstagio> search(String query, Long alunoId, Pageable pageable) {
        List<Candidatura> candidaturas = candidaturaService.listarCandidaturasPorAluno(alunoId);

        Set<Long> ofertasCandidatas = candidaturas.stream()
                .map(c -> c.getOfertaEstagio().getIdOfertaEstagio())
                .collect(Collectors.toSet());

        return ofertaEstagioRepository.findByQueryAndExcludingCandidaturas(query, ofertasCandidatas, pageable);
    }

    public void delete(Long idOfertaEstagio) {
        OfertaEstagio oferta = ofertaEstagioRepository.findById(idOfertaEstagio)
                .orElseThrow(() -> new IllegalArgumentException("Oferta não encontrada"));

        List<Candidatura> candidaturas = candidaturaService.listarCandidaturasPorOferta(idOfertaEstagio);
        for (Candidatura candidatura : candidaturas) {
            candidaturaService.delete(candidatura.getIdCandidatura());
        }

        ofertaEstagioRepository.delete(oferta);
    }

    //test
}


