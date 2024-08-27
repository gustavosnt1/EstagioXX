package com.estagioxx.EstagioX.repositories;

import com.estagioxx.EstagioX.entities.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByUsername(String username);
}
