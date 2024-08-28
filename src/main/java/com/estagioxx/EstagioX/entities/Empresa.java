package com.estagioxx.EstagioX.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_empresas")
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmpresa")
    private Long idEmpresa;
    @Column(name = "nomeEmpresa",nullable = false)
    private String nome;
    @Column(name = "cnpjEmpresa",nullable = false)
    private String cnpj;
    @Column(name = "emailEmpresa",nullable = false)
    private String email;
    @Column(name = "telefoneEmpresa",nullable = false)
    private String telefone;
    @Column(name = "enderecoEmpresa",nullable = false)
    private String endereco;
    @Column(name = "pessoaContatoEmpresa",nullable = false)
    private String pessoaContato;
    @Column(name = "atividadePrincipalEmpresa",nullable = false)
    private String atividadePrincipal;
    @Column(name = "urlEmpresa", nullable = false)
    private String urlEmpresa;
    @Column(name = "pdfEmpresa", nullable = true)
    private byte[] pdfEmpresa;


    @OneToMany(mappedBy = "empresas", fetch = FetchType.LAZY)
    private List<OfertaEstagio> offers = new ArrayList<>();


    public Empresa() {

    }

    public Empresa(Long idEmpresa, String nome, String cnpj, String email, String telefone, String endereco, String pessoaContato, String atividadePrincipal, String urlEmpresa, byte[] pdfEmpresa) {
        this.idEmpresa = idEmpresa;
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.pessoaContato = pessoaContato;
        this.atividadePrincipal = atividadePrincipal;
        this.urlEmpresa = urlEmpresa;
        this.pdfEmpresa = pdfEmpresa;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getPessoaContato() {
        return pessoaContato;
    }

    public void setPessoaContato(String pessoaContato) {
        this.pessoaContato = pessoaContato;
    }

    public String getAtividadePrincipal() {
        return atividadePrincipal;
    }

    public void setAtividadePrincipal(String atividadePrincipal) {
        this.atividadePrincipal = atividadePrincipal;
    }

    public String getUrlEmpresa() {
        return urlEmpresa;
    }

    public void setUrlEmpresa(String urlEmpresa) {
        this.urlEmpresa = urlEmpresa;
    }

    public byte[] getPdfEmpresa() {
        return pdfEmpresa;
    }

    public void setPdfEmpresa(byte[] pdfEmpresa) {
        this.pdfEmpresa = pdfEmpresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(idEmpresa, empresa.idEmpresa);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idEmpresa);
    }
}
