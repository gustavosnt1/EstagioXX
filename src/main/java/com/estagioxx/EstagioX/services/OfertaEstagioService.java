package com.estagioxx.EstagioX.services;

import com.estagioxx.EstagioX.entities.Candidatura;
import com.estagioxx.EstagioX.entities.Empresa;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
import com.estagioxx.EstagioX.repositories.EmpresaRepository;
import com.estagioxx.EstagioX.repositories.OfertaEstagioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfertaEstagioService {

    @Autowired
    private OfertaEstagioRepository ofertaEstagioRepository;

    @Lazy
    @Autowired
    private EmpresaService empresaService;

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


    public List<OfertaEstagio> search(String query, Long alunoId) {
        // Obtém todas as ofertas
        List<OfertaEstagio> todasOfertas = ofertaEstagioRepository.findAll();

        // Obtém as candidaturas do aluno
        List<Candidatura> candidaturas = candidaturaService.listarCandidaturasPorAluno(alunoId);

        // Filtra ofertas que já foram candidatas
        Set<Long> ofertasCandidatas = candidaturas.stream()
                .map(c -> c.getOfertaEstagio().getIdOfertaEstagio())
                .collect(Collectors.toSet());

        // Filtra ofertas disponíveis com base no termo de pesquisa
        return todasOfertas.stream()
                .filter(o -> !ofertasCandidatas.contains(o.getIdOfertaEstagio()) &&
                        (o.getAtividadePrincipal().toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(o.getValorPago()).contains(query)))
                .collect(Collectors.toList());
    }
}
