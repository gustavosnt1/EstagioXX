package com.estagioxx.EstagioX.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_estagios")
public class Estagio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstagio")
    private Long idEstagio;

    @ManyToOne
    @JoinColumn(name = "idAluno", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "idOfertaEstagio", nullable = false)
    private OfertaEstagio ofertaEstagio;

    @Column(name = "dataInicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "dataTermino", nullable = false)
    private LocalDate dataTermino;

    @Column(name = "valorEstagio", nullable = false)
    private double valorEstagio;


    public Long getIdEstagio() {
        return idEstagio;
    }

    public void setIdEstagio(Long idEstagio) {
        this.idEstagio = idEstagio;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public OfertaEstagio getOfertaEstagio() {
        return ofertaEstagio;
    }

    public void setOfertaEstagio(OfertaEstagio ofertaEstagio) {
        this.ofertaEstagio = ofertaEstagio;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public double getValorEstagio() {
        return valorEstagio;
    }

    public void setValorEstagio(double valorEstagio) {
        this.valorEstagio = valorEstagio;
    }
}