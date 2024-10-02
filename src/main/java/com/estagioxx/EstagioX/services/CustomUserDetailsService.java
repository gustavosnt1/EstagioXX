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

        // 1. Procurar no repositório de Aluno
        Aluno aluno = alunoRepository.findByUsername(username).orElse(null);
        if (aluno != null) {
            System.out.println("Usuário encontrado no repositório de Aluno: " + aluno.getUsername());
            return User.builder()
                    .username(aluno.getUsername())
                    .password(aluno.getPassword())  // Assumindo que a senha já está criptografada
                    .roles("ALUNO")  // Defina a role de acordo com o tipo de usuário
                    .build();
        }

        // 2. Procurar no repositório de Empresa
        Empresa empresa = empresaRepository.findByEmail(username).orElse(null);
        if (empresa != null) {
            System.out.println("Usuário encontrado no repositório de Empresa: " + empresa.getEmail());
            return User.builder()
                    .username(empresa.getEmail())
                    .password(empresa.getPassword())
                    .roles("EMPRESA")
                    .build();
        }

        // 3. Procurar no repositório de Coordenador
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

        // Caso o usuário não seja encontrado, lançar exceção
        System.out.println("Usuário não encontrado: " + username);
        throw new UsernameNotFoundException("Usuário não encontrado: " + username);
    }
}
