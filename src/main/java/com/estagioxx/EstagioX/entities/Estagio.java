package com.estagioxx.EstagioX.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_estagio")
public class Estagio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "oferta_estagio_id")
    private OfertaEstagio ofertaEstagio;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

/*    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private Double valor;*/

    public Estagio() {

    }

    public Estagio(Long id, OfertaEstagio ofertaEstagio, LocalDate dataInicio, LocalDate dataTermino, Aluno aluno, Double valor) {
        this.id = id;
        this.ofertaEstagio = ofertaEstagio;
        this.aluno = aluno;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

/*    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estagio estagio = (Estagio) o;
        return Objects.equals(id, estagio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}