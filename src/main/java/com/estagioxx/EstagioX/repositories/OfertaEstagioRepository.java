package com.estagioxx.EstagioX.repositories;

import com.estagioxx.EstagioX.entities.Empresa;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfertaEstagioRepository extends JpaRepository<OfertaEstagio, Long> {
    List<OfertaEstagio> findByEmpresas(Empresa empresa);
    Page<OfertaEstagio> findByEmpresas(Empresa empresa, Pageable pageable);
}
