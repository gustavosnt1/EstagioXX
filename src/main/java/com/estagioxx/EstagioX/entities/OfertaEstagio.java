package com.estagioxx.EstagioX.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_ofertas_estagio")
public class OfertaEstagio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOfertaEstagio;
    @Column(name = "atividadePrincipalEstagio", nullable = false)
    private String atividadePrincipal;
    @Column(name = "chSemanalEstagio", nullable = false)
    private String chSemanal;
    @Column(name = "valorEstagio", nullable = false)
    private double valorPago;
    @Column(name = "valeTransporteEstagio", nullable = false)
    private double valeTransporte;
    @Column(name = "preRequisitosEstagio", nullable = false)
    private String preRequisitos;
    @Column(name = "habilidadesNecessariaEstagio", nullable = false)
    private String habilidadesNecessaria;
    @Column(name = "habilidadesDesejavelEstagio", nullable = false)
    private String habilidadesDesejavel;

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
