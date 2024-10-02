package com.estagioxx.EstagioX.repositories;

import com.estagioxx.EstagioX.entities.Empresa;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import  java.util.List;
import java.util.Set;

public interface OfertaEstagioRepository extends JpaRepository<OfertaEstagio, Long> {
    @Query("SELECT o FROM OfertaEstagio o WHERE (o.atividadePrincipal LIKE %:query% OR CAST(o.valorPago AS string) LIKE %:query%) AND o.idOfertaEstagio NOT IN :ofertasCandidatas")
    Page<OfertaEstagio> findByQueryAndExcludingCandidaturas(String query, Set<Long> ofertasCandidatas, Pageable pageable);
    List<OfertaEstagio> findByEmpresas(Empresa empresa);
}
