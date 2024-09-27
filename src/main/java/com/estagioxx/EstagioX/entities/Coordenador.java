package com.estagioxx.EstagioX.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "tb_coordenadores")
public class Coordenador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCoordenador")
    private Long idCoordenador;

    @NotBlank(message = "O nome não pode estar vazio.")
    @Column(name = "nomeCoordenador", nullable = false)
    private String nome;

    @NotBlank(message = "O username não pode estar vazio.")
    @Column(name = "usernameCoordenador", nullable = false)
    private String username;

    @NotBlank(message = "A senha não pode estar vazia.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    @Column(name = "passwordCoordenador", nullable = false)
    private String password;


    public Coordenador() {}

    public Coordenador(Long idCoordenador, String nome, String username, String password) {
        this.idCoordenador = idCoordenador;
        this.nome = nome;
        this.username = username;
        this.password = password;
    }

    public Long getIdCoordenador() {
        return idCoordenador;
    }

    public void setIdCoordenador(Long idCoordenador) {
        this.idCoordenador = idCoordenador;
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
}