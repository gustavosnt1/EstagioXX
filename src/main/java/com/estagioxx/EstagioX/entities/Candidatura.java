package com.estagioxx.EstagioX.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tb_candidaturas")
public class Candidatura implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCandidatura")
    private Long idCandidatura;
    @ManyToOne
    @JoinColumn(name = "idAluno")
    private Aluno aluno;
    @ManyToOne
    @JoinColumn(name = "idOfertaEstagio")
    private OfertaEstagio ofertaEstagio;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusCandidatura status;


    public Long getIdCandidatura() {
        return idCandidatura;
    }

    public void setIdCandidatura(Long idCandidatura) {
        this.idCandidatura = idCandidatura;
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

    public StatusCandidatura getStatus() {
        return status;
    }

    public void setStatus(StatusCandidatura status) {
        this.status = status;
    }

    public enum StatusCandidatura {
        PENDENTE, ACEITA, REJEITADA
    }
}