package com.estagioxx.EstagioX.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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

    @NotBlank(message = "O nome da empresa não pode estar vazio.")
    @Column(name = "nomeEmpresa", nullable = false)
    private String nome;

    @NotBlank(message = "O CNPJ não pode estar vazio.")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter 14 dígitos.")
    @Column(name = "cnpjEmpresa", nullable = false)
    private String cnpj;

    @NotBlank(message = "O e-mail não pode estar vazio.")
    @Column(name = "emailEmpresa", nullable = false)
    private String email;

    @Pattern(regexp = "^\\d{10,11}$", message = "O telefone deve conter entre 10 a 11 dígitos.")
    @Column(name = "telefoneEmpresa", nullable = false)
    private String telefone;

    @NotBlank(message = "O endereço não pode estar vazio.")
    @Column(name = "enderecoEmpresa", nullable = false)
    private String endereco;

    @NotBlank(message = "A pessoa de contato não pode estar vazia.")
    @Column(name = "pessoaContatoEmpresa", nullable = false)
    private String pessoaContato;

    @NotBlank(message = "A atividade principal não pode estar vazia.")
    @Column(name = "atividadePrincipalEmpresa", nullable = false)
    private String atividadePrincipal;

    @NotBlank(message = "A URL da empresa não pode estar vazia.")
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
