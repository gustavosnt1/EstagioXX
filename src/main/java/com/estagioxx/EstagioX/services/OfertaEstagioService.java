package com.estagioxx.EstagioX.services;

import com.estagioxx.EstagioX.entities.Empresa;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
import com.estagioxx.EstagioX.repositories.EmpresaRepository;
import com.estagioxx.EstagioX.repositories.OfertaEstagioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfertaEstagioService {

    @Autowired
    private OfertaEstagioRepository ofertaEstagioRepository;

    @Lazy
    @Autowired
    private EmpresaService empresaService;

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
}
