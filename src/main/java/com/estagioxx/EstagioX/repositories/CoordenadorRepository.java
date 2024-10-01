package com.estagioxx.EstagioX.repositories;

import com.estagioxx.EstagioX.entities.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {
    Optional<Coordenador> findByUsername(String username);
}
