package com.estagioxx.EstagioX.entities;

import jakarta.persistence.*;

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
    @Column(name = "nomeAluno",nullable = false)
    private String nome;
    @Column(name = "usernameAluno",nullable = false)
    private String username;
    @Column(name = "passwordAluno",nullable = false)
    private String password;
    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "habilidades")
    private Set<String> habilidades;

    public Aluno() {

    }

    public Aluno(Long idAluno, String nome, String username, Set<String> habilidades, String password) {
        this.idAluno = idAluno;
        this.nome = nome;
        this.username = username;
        this.habilidades = habilidades;
        this.password = password;
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
