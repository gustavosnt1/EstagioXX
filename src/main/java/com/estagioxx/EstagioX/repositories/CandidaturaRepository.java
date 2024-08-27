package com.estagioxx.EstagioX.repositories;

import com.estagioxx.EstagioX.entities.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
    @Query("SELECT c FROM Candidatura c WHERE c.ofertaEstagio.idOfertaEstagio = :idOfertaEstagio")
    List<Candidatura> findByOfertaEstagio_IdOfertaEstagio(@Param("idOfertaEstagio") Long idOfertaEstagio);

    @Query("SELECT c FROM Candidatura c WHERE c.aluno.idAluno = :idAluno")
    List<Candidatura> findByAluno_IdAluno(@Param("idAluno") Long idAluno);
}
