package com.estagioxx.EstagioX.services;

import com.estagioxx.EstagioX.entities.*;
import com.estagioxx.EstagioX.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CoordenadorService {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private OfertaEstagioRepository ofertaEstagioRepository;

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private EstagioService estagioService;

    public Coordenador save(Coordenador coordenador) {
        return coordenadorRepository.save(coordenador);
    }

    public boolean authenticate(String username, String password) {
        Optional<Coordenador> coordenadorOptional = coordenadorRepository.findByUsername(username);

        if (coordenadorOptional.isPresent()) {
            Coordenador coordenador = coordenadorOptional.get();
            return coordenador.getPassword().equals(password);
        }
        return false;
    }


    public List<OfertaEstagio> listarTodasOfertas() {
        return ofertaEstagioRepository.findAll(); //
    }


    public List<Candidatura> listarCandidatosPorOferta(Long ofertaId) {
        return candidaturaRepository.findByOfertaEstagio_IdOfertaEstagio(ofertaId);
    }

    public List<Estagio> listarEstagios() {
        return estagioService.listarEstagios();
    }

    public Coordenador findByUsername(String username) {
        return coordenadorRepository.findByUsername(username).orElse(null);
    }
}
