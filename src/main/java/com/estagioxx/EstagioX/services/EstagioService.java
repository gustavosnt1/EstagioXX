package com.estagioxx.EstagioX.services;

import com.estagioxx.EstagioX.entities.Estagio;
import com.estagioxx.EstagioX.repositories.EstagioRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class EstagioService {
    @Autowired
    private EstagioRepository estagioRepository;

    public void save(Estagio estagio) {
        estagioRepository.save(estagio);
    }

    public List<Estagio> listarEstagios() {
        return estagioRepository.findAll();
    }

    public Estagio findById(Long id) {
        return estagioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estágio não encontrado para o ID: " + id));
    }

    public byte[] gerarTermoEstagio(Estagio estagio) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);



            document.add(new Paragraph("TERMO DE COMPROMISSO DE ESTÁGIO").setBold().setFontSize(16));


            document.add(new Paragraph("Empresa Concedente:").setBold());

            document.add(new Paragraph("Nome: " + estagio.getOfertaEstagio().getEmpresas().getNome()));
            document.add(new Paragraph("CNPJ: " + estagio.getOfertaEstagio().getEmpresas().getCnpj()));
            document.add(new Paragraph("Endereço: " + estagio.getOfertaEstagio().getEmpresas().getEndereco()));
            document.add(new Paragraph("Telefone: " + estagio.getOfertaEstagio().getEmpresas().getTelefone()));
            document.add(new Paragraph("Pessoa Contato: " + estagio.getOfertaEstagio().getEmpresas().getPessoaContato()));


            document.add(new Paragraph("Estagiário:").setBold());

            document.add(new Paragraph("Nome: " + estagio.getAluno().getNome() + " " + estagio.getAluno().getSobrenome()));
            document.add(new Paragraph("Instituição de Ensino: " + estagio.getAluno().getFaculdade()));


            document.add(new Paragraph("Condições do Estágio:").setBold());

            document.add(new Paragraph("Atividade Princial do Estágio: " + estagio.getOfertaEstagio().getAtividadePrincipal()));
            document.add(new Paragraph("Data de Início: " + estagio.getDataInicio().toString()));
            document.add(new Paragraph("Data de Término: " + estagio.getDataTermino().toString()));
            document.add(new Paragraph("Remuneração: O estagiário receberá uma bolsa-auxílio de R$ " + estagio.getValorEstagio() + " por mês."));


            document.add(new Paragraph("Obrigações:").setBold());
            document.add(new Paragraph("Estagiário: Cumprir a carga horária e atividades definidas, manter confidencialidade das informações, e respeitar o código de conduta da empresa."));
            document.add(new Paragraph("Empresa: Prover os recursos necessários para a realização do estágio, acompanhar o desenvolvimento do estagiário e fornecer avaliação periódica."));


            document.add(new Paragraph("Assinaturas:").setBold());
            document.add(new Paragraph(estagio.getOfertaEstagio().getEmpresas().getPessoaContato() + "\nEmpresa Concedente"));
            document.add(new Paragraph(estagio.getAluno().getNome() + " " + estagio.getAluno().getSobrenome() + "\nEstagiário"));




            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
