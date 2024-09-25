package com.estagioxx.EstagioX.services;

import com.estagioxx.EstagioX.entities.Aluno;
import com.estagioxx.EstagioX.entities.Estagio;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
import com.estagioxx.EstagioX.repositories.CandidaturaRepository;
import com.estagioxx.EstagioX.repositories.EstagioRepository;
import com.estagioxx.EstagioX.repositories.OfertaEstagioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EstagioService {
    @Autowired
    private EstagioRepository estagioRepository;

    public void save(Estagio estagio) {
        estagioRepository.save(estagio);
    }

    public List<Estagio> listarEstagios() {
        return estagioRepository.findAll();
    }


}
