package com.estagioxx.EstagioX.services;


import com.estagioxx.EstagioX.entities.Empresa;
import com.estagioxx.EstagioX.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;


    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Empresa findById(Long idEmpresa) {
        Empresa empresa = null;
        Optional<Empresa> empresax = empresaRepository.findById(idEmpresa);
        if (empresax.isPresent()) {
            empresa = empresax.get();
        }
        return empresa;
    }

    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public boolean authenticate(String email) {
        Optional<Empresa> empresaOptional = empresaRepository.findByEmail(email);


        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            return empresa.getEmail().equals(email);
        }

        return false;
    }

    public Empresa findByEmail(String email) {
        return empresaRepository.findByEmail(email).orElse(null);
    }
}
