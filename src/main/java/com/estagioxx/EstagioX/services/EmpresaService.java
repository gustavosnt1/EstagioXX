package com.estagioxx.EstagioX.services;


import com.estagioxx.EstagioX.entities.*;
import com.estagioxx.EstagioX.repositories.CandidaturaRepository;
import com.estagioxx.EstagioX.repositories.EmpresaRepository;
import com.estagioxx.EstagioX.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private EstagioService estagioService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        String encodedPassword = passwordEncoder.encode(empresa.getPassword());
        empresa.setPassword(encodedPassword); // Definir a senha criptografada
        Role roleEmpresa = roleRepository.findByName("ROLE_EMPRESA");
        empresa.setRole(roleEmpresa);
        return empresaRepository.save(empresa);
    }

    public boolean authenticate(String username, String password) {
        System.out.println("Tentando autenticar usuário: " + username);
        Optional<Empresa> empresaOptional = empresaRepository.findByEmail(username);

        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            // Utilize passwordEncoder para comparar a senha
            boolean passwordMatches = passwordEncoder.matches(password, empresa.getPassword());
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

    public Empresa findByEmail(String email) {
        return empresaRepository.findByEmail(email).orElse(null);
    }

    public List<Candidatura> listarCandidatosPorOferta(Long ofertaId) {
        return candidaturaRepository.findByOfertaEstagio_IdOfertaEstagio(ofertaId);
    }

    public Page<Empresa> findAll(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }


    public void update(Long id, Empresa empresaAtualizada) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(id);

        if (empresaOptional.isPresent()) {
            Empresa empresaExistente = empresaOptional.get();

            empresaExistente.setNome(empresaAtualizada.getNome());
            empresaExistente.setCnpj(empresaAtualizada.getCnpj());
            empresaExistente.setEmail(empresaAtualizada.getEmail());
            empresaExistente.setTelefone(empresaAtualizada.getTelefone());
            empresaExistente.setEndereco(empresaAtualizada.getEndereco());
            empresaExistente.setPessoaContato(empresaAtualizada.getPessoaContato());
            empresaExistente.setAtividadePrincipal(empresaAtualizada.getAtividadePrincipal());
            empresaExistente.setUrlEmpresa(empresaAtualizada.getUrlEmpresa());

            if (empresaAtualizada.getPdfEmpresa() != null) {
                empresaExistente.setPdfEmpresa(empresaAtualizada.getPdfEmpresa());
            }

            empresaRepository.save(empresaExistente);
        } else {
            throw new RuntimeException("Empresa não encontrada para o ID: " + id);
        }
    }
}

