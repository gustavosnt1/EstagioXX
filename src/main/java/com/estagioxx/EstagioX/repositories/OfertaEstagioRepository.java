package com.estagioxx.EstagioX.repositories;

import com.estagioxx.EstagioX.entities.Empresa;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OfertaEstagioRepository extends JpaRepository<OfertaEstagio, Long> {

    Page<OfertaEstagio> findByEmpresas(Empresa empresa, Pageable pageable);
}
