package com.estagioxx.EstagioX.Config;

import com.estagioxx.EstagioX.entities.Role;
import com.estagioxx.EstagioX.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public CommandLineRunner loadInitialRoles() {
        return args -> {
            // Verifica se a role 'ROLE_COORDENADOR' existe, caso não exista, cria e salva no banco de dados
            if (roleRepository.findByName("ROLE_COORDENADOR") == null) {
                roleRepository.save(new Role("ROLE_COORDENADOR"));
            }
            if (roleRepository.findByName("ROLE_ALUNO") == null) {
                roleRepository.save(new Role("ROLE_ALUNO"));
            }
            if (roleRepository.findByName("ROLE_EMPRESA") == null) {
                roleRepository.save(new Role("ROLE_EMPRESA"));
            }
        };
    }
}
