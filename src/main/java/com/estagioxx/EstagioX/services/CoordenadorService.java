package com.estagioxx.EstagioX.services;

import com.estagioxx.EstagioX.entities.Candidatura;
import com.estagioxx.EstagioX.entities.Coordenador;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
import com.estagioxx.EstagioX.repositories.CandidaturaRepository;
import com.estagioxx.EstagioX.repositories.CoordenadorRepository;
import com.estagioxx.EstagioX.repositories.OfertaEstagioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordenadorService {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private OfertaEstagioRepository ofertaEstagioRepository;

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    public Coordenador save(Coordenador coordenador) {
        return coordenadorRepository.save(coordenador);
    }

    public Coordenador authenticate(String username, String password) {
        Coordenador coordenador = coordenadorRepository.findByUsername(username);
        if (coordenador != null && coordenador.getPassword().equals(password)) {
            return coordenador;
        }
        return null;
    }

    // Listar todas as ofertas de estágio
    public List<OfertaEstagio> listarTodasOfertas() {
        return ofertaEstagioRepository.findAll(); // Retorna todas as ofertas de estágio
    }

    // Listar todos os candidatos a uma oferta específica
    public List<Candidatura> listarCandidatosPorOferta(Long ofertaId) {
        return candidaturaRepository.findByOfertaEstagio_IdOfertaEstagio(ofertaId); // Retorna todas as candidaturas para a oferta específica
    }
}
