package com.estagioxx.EstagioX.repositories;

import com.estagioxx.EstagioX.entities.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByEmail(String email);
    Page<Empresa> findByAtividadePrincipal(String atividadePrincipal, Pageable pageable);
}
