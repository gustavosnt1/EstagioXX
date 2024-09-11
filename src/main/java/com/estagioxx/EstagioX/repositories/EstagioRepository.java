package com.estagioxx.EstagioX.repositories;

import com.estagioxx.EstagioX.entities.Estagio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstagioRepository extends JpaRepository<Estagio, Long> {
    boolean existsByOfertaEstagio_IdOfertaEstagio(Long idOfertaEstagio);
}
