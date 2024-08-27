package com.estagioxx.EstagioX.repositories;

import com.estagioxx.EstagioX.entities.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {
    Coordenador findByUsername(String username);
}
