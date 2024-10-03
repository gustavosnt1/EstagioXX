package com.estagioxx.EstagioX.services;

import com.estagioxx.EstagioX.entities.Aluno;
import com.estagioxx.EstagioX.entities.Coordenador;
import com.estagioxx.EstagioX.entities.Empresa;
import com.estagioxx.EstagioX.repositories.AlunoRepository;
import com.estagioxx.EstagioX.repositories.CoordenadorRepository;
import com.estagioxx.EstagioX.repositories.EmpresaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AlunoRepository alunoRepository;
    private final EmpresaRepository empresaRepository;
    private final CoordenadorRepository coordenadorRepository;

    public CustomUserDetailsService(AlunoRepository alunoRepo, EmpresaRepository empresaRepo, CoordenadorRepository coordRepo) {
        this.alunoRepository = alunoRepo;
        this.empresaRepository = empresaRepo;
        this.coordenadorRepository = coordRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Tentativa de login com username/email: " + username);


        Aluno aluno = alunoRepository.findByUsername(username).orElse(null);
        if (aluno != null) {
            System.out.println("Usuário encontrado no repositório de Aluno: " + aluno.getUsername());
            return User.builder()
                    .username(aluno.getUsername())
                    .password(aluno.getPassword())
                    .roles("ALUNO")
                    .build();
        }


        Empresa empresa = empresaRepository.findByEmail(username).orElse(null);
        if (empresa != null) {
            System.out.println("Usuário encontrado no repositório de Empresa: " + empresa.getEmail());
            return User.builder()
                    .username(empresa.getEmail())
                    .password(empresa.getPassword())
                    .roles("EMPRESA")
                    .build();
        }


        Coordenador coordenador = coordenadorRepository.findByUsername(username).orElse(null);
        if (coordenador != null) {
            System.out.println("Usuário encontrado no repositório de Coordenador: " + coordenador.getUsername());
            System.out.println("Nome do coordenador: " + coordenador.getNome()); // Adicione essa linha para ver o nome
            return User.builder()
                    .username(coordenador.getUsername())
                    .password(coordenador.getPassword())
                    .roles("COORDENADOR")
                    .build();
        }


        System.out.println("Usuário não encontrado: " + username);
        throw new UsernameNotFoundException("Usuário não encontrado: " + username);
    }
}
