package com.estagioxx.EstagioX.repositories;

import com.estagioxx.EstagioX.entities.Estagio;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EstagioRepository extends JpaRepository<Estagio, Long> {

}
