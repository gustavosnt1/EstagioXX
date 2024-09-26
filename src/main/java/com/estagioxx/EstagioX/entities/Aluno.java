package com.estagioxx.EstagioX.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_alunos")
public class Aluno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAluno")
    private Long idAluno;

    @NotBlank(message = "O nome não pode estar vazio.")
    @Column(name = "nomeAluno",nullable = false)
    private String nome;
    @NotBlank(message = "O sobrenome não pode estar vazio.")
    @Column(name = "sobrenomeAluno",nullable = false)
    private String sobrenome;

    @NotBlank(message = "O username não pode estar vazio.")
    @Column(name = "usernameAluno",nullable = false)
    private String username;

    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    @Column(name = "passwordAluno",nullable = false)
    private String password;

    @NotBlank(message = "A faculdade não pode estar vazia.")
    @Column(name = "faculdadeAluno",nullable = false)
    private String faculdade;

    @Pattern(regexp = "^\\d{10,11}$", message = "O telefone deve conter entre 10 a 11 dígitos.")
    @Column(name = "telefoneAluno",nullable = false)
    private String telefone;
    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "habilidades")
    private Set<String> habilidades;

    public Aluno() {

    }

    public Aluno(Long idAluno, String nome, String sobrenome, String username, String password, String faculdade, String telefone) {
        this.idAluno = idAluno;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.username = username;
        this.password = password;
        this.faculdade = faculdade;
        this.telefone = telefone;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(String faculdade) {
        this.faculdade = faculdade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Set<String> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(Set<String> habilidades) {
        this.habilidades = habilidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return Objects.equals(idAluno, aluno.idAluno);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idAluno);
    }
}
