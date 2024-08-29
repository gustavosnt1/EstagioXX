package com.estagioxx.EstagioX.services;

import com.estagioxx.EstagioX.entities.Aluno;
import com.estagioxx.EstagioX.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    public Aluno findById(Long idAluno) {
        Aluno aluno = null;
        Optional<Aluno> alunox = alunoRepository.findById(idAluno);
        if (alunox.isPresent()) {
            aluno = alunox.get();
        }
        return aluno;
    }

    public Aluno save(Aluno aluno) {
        return alunoRepository.save(aluno);
    }


    public boolean authenticate(String username, String password) {
        Optional<Aluno> alunoOptional = alunoRepository.findByUsername(username);


        if (alunoOptional.isPresent()) {
            Aluno aluno = alunoOptional.get();
            return aluno.getPassword().equals(password);
        }

        return false;
    }

    public Aluno findByUsername(String username) {
        return alunoRepository.findByUsername(username).orElse(null);
    }

}
