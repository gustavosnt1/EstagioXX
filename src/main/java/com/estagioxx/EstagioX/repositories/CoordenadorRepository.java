package com.estagioxx.EstagioX.repositories;

import com.estagioxx.EstagioX.entities.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {
    @Query("SELECT c FROM Coordenador c WHERE c.username = ?1")
    Optional<Coordenador> findByUsername(String username);
}
