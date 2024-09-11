package com.estagioxx.EstagioX.services;

import com.estagioxx.EstagioX.entities.Estagio;
import com.estagioxx.EstagioX.repositories.EstagioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstagioService {

    @Autowired
    private EstagioRepository estagioRepository;

    public void save(Estagio estagio) {
        estagioRepository.save(estagio);
    }

    public boolean existsByOfertaEstagio(Long ofertaEstagioId) {
        return estagioRepository.existsByOfertaEstagio_IdOfertaEstagio(ofertaEstagioId);
    }
}
