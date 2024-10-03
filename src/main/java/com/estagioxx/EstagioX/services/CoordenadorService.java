package com.estagioxx.EstagioX.services;

import com.estagioxx.EstagioX.entities.*;
import com.estagioxx.EstagioX.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class CoordenadorService {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private OfertaEstagioRepository ofertaEstagioRepository;

    @Autowired
    private CandidaturaRepository candidaturaRepository;
    @Autowired
    private EstagioRepository estagioRepository;

    @Autowired
    private EstagioService estagioService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmpresaRepository empresaRepository;

    public Coordenador save(Coordenador coordenador) {

        String encodedPassword = passwordEncoder.encode(coordenador.getPassword());
        coordenador.setPassword(encodedPassword); // Definir a senha criptografada
        Role roleCoordenador = roleRepository.findByName("ROLE_COORDENADOR");


        // Verifica se a role foi encontrada, caso contrário, lança uma exceção
        if (roleCoordenador == null) {
            throw new RuntimeException("Role 'ROLE_COORDENADOR' não encontrada no banco de dados!");
        }

        coordenador.setRole(roleCoordenador);

        return coordenadorRepository.save(coordenador);
    }

    public boolean authenticate(String username, String password) {
        System.out.println("Tentando autenticar usuário: " + username);
        Optional<Coordenador> coordenadorOptional = coordenadorRepository.findByUsername(username);

        if (coordenadorOptional.isPresent()) {
            Coordenador coordenador = coordenadorOptional.get();
            // Utilize passwordEncoder para comparar a senha
            boolean passwordMatches = passwordEncoder.matches(password, coordenador.getPassword());
            if (passwordMatches) {
                return true; // Senha correta
            } else {
                System.out.println("Senha incorreta para o usuário: " + username);
            }
        } else {
            System.out.println("Usuário não encontrado: " + username);
        }
        return false; // Retorna false se o usuário não existir ou a senha estiver incorreta
    }


    public List<OfertaEstagio> listarTodasOfertas() {
        return ofertaEstagioRepository.findAll(); //
    }


    public List<Candidatura> listarCandidatosPorOferta(Long ofertaId) {
        return candidaturaRepository.findByOfertaEstagio_IdOfertaEstagio(ofertaId);
    }

    /*public List<Estagio> listarEstagios() {
        return estagioService.listarEstagios();
    }*/

    public Page<Estagio> listarEstagios(Pageable pageable) {
        return estagioRepository.findAll(pageable);
    }

    public Coordenador findByUsername(String username) {
        return coordenadorRepository.findByUsername(username).orElse(null);
    }

    public Page<OfertaEstagio> listarTodasOfertasPaginadas(Pageable pageable) {
        return ofertaEstagioRepository.findAll(pageable);
    }
}
