package com.estagioxx.EstagioX.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_ofertas_estagio")
public class OfertaEstagio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOfertaEstagio;
    @NotBlank(message = "A atividade principal é obrigatória.")
    @Column(name = "atividadePrincipalEstagio", nullable = false)
    private String atividadePrincipal;

    @NotBlank(message = "A carga horária semanal é obrigatória.")
    @Size(max = 20, message = "A carga horária semanal não pode exceder 20 caracteres.")
    @Column(name = "chSemanalEstagio", nullable = false)
    private String chSemanal;

    @NotNull(message = "O valor pago é obrigatório.")
    @Positive(message = "O valor pago deve ser positivo.")
    @Column(name = "valorEstagio", nullable = false)
    private double valorPago;

    @NotNull(message = "O valor do vale transporte é obrigatório.")
    @Positive(message = "O valor do vale transporte deve ser positivo.")
    @Column(name = "valeTransporteEstagio", nullable = false)
    private double valeTransporte;

    @NotBlank(message = "Os pré-requisitos são obrigatórios.")
    @Column(name = "preRequisitosEstagio", nullable = false)
    private String preRequisitos;

    @NotBlank(message = "As habilidades necessárias são obrigatórias.")
    @Column(name = "habilidadesNecessariaEstagio", nullable = false)
    private String habilidadesNecessaria;

    @NotBlank(message = "As habilidades desejáveis são obrigatórias.")
    @Column(name = "habilidadesDesejavelEstagio", nullable = false)
    private String habilidadesDesejavel;


    @Column(name = "preenchida", nullable = false)
    private boolean preenchida = false;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresas;


    public OfertaEstagio() {

    }

    public OfertaEstagio(Long idOfertaEstagio,String atividadePrincipal, String chSemanal, double valorPago, double valeTransporte, String preRequisitos, String habilidadesNecessaria, String habilidadesDesejavel, Empresa empresas) {
        this.idOfertaEstagio = idOfertaEstagio;
        this.atividadePrincipal = atividadePrincipal;
        this.chSemanal = chSemanal;
        this.valorPago = valorPago;
        this.valeTransporte = valeTransporte;
        this.preRequisitos = preRequisitos;
        this.habilidadesNecessaria = habilidadesNecessaria;
        this.habilidadesDesejavel = habilidadesDesejavel;
        this.empresas = empresas;
    }

    public Long getIdOfertaEstagio() {
        return idOfertaEstagio;
    }

    public void setIdOfertaEstagio(Long idOfertaEstagio) {
        this.idOfertaEstagio = idOfertaEstagio;
    }

    public String getAtividadePrincipal() {
        return atividadePrincipal;
    }

    public void setAtividadePrincipal(String atividadePrincipal) {
        this.atividadePrincipal = atividadePrincipal;
    }

    public String getChSemanal() {
        return chSemanal;
    }

    public void setChSemanal(String chSemanal) {
        this.chSemanal = chSemanal;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public double getValeTransporte() {
        return valeTransporte;
    }

    public void setValeTransporte(double valeTransporte) {
        this.valeTransporte = valeTransporte;
    }

    public String getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(String preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    public String getHabilidadesNecessaria() {
        return habilidadesNecessaria;
    }

    public void setHabilidadesNecessaria(String habilidadesNecessaria) {
        this.habilidadesNecessaria = habilidadesNecessaria;
    }

    public String getHabilidadesDesejavel() {
        return habilidadesDesejavel;
    }

    public void setHabilidadesDesejavel(String habilidadesDesejavel) {
        this.habilidadesDesejavel = habilidadesDesejavel;
    }

    public Empresa getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresa empresas) {
        this.empresas = empresas;
    }

    public boolean isPreenchida() {
        return preenchida;
    }

    public void setPreenchida(boolean preenchida) {
        this.preenchida = preenchida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfertaEstagio that = (OfertaEstagio) o;
        return Objects.equals(idOfertaEstagio, that.idOfertaEstagio);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idOfertaEstagio);
    }
}
