package com.estagioxx.EstagioX.services;

import com.estagioxx.EstagioX.entities.Aluno;
import com.estagioxx.EstagioX.entities.Role;
import com.estagioxx.EstagioX.repositories.AlunoRepository;
import com.estagioxx.EstagioX.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        String encodedPassword = passwordEncoder.encode(aluno.getPassword());
        aluno.setPassword(encodedPassword); // Definir a senha criptografada

        Role roleAluno = roleRepository.findByName("ROLE_ALUNO");
        aluno.setRole(roleAluno);

        return alunoRepository.save(aluno);
    }


    public boolean authenticate(String username, String password) {
        System.out.println("Tentando autenticar usuário: " + username);
        Optional<Aluno> alunoOptional = alunoRepository.findByUsername(username);

        if (alunoOptional.isPresent()) {
            Aluno aluno = alunoOptional.get();
            boolean passwordMatches = passwordEncoder.matches(password, aluno.getPassword());
            if (passwordMatches) {
                return true; // Senha correta
            } else {
                System.out.println("Senha incorreta para o usuário: " + username);
            }
        } else {
            System.out.println("Usuário não encontrado: " + username);
        }
        return false;
    }

    public Aluno findByUsername(String username) {
        return alunoRepository.findByUsername(username).orElse(null);
    }

}
