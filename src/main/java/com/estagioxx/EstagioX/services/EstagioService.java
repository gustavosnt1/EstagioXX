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


            document.add(new Paragraph("Termo de Estágio"));
            document.add(new Paragraph("Nome do Aluno: " + estagio.getAluno().getNome()));
            document.add(new Paragraph("Atividade Princial do Estágio: " + estagio.getOfertaEstagio().getAtividadePrincipal()));
            document.add(new Paragraph("Data de Início: " + estagio.getDataInicio().toString()));
            document.add(new Paragraph("Data de Término: " + estagio.getDataTermino().toString()));
            document.add(new Paragraph("Valor do Estágio: " + estagio.getValorEstagio()));
            document.add(new Paragraph("NomeEmpresa: " + estagio.getOfertaEstagio().getEmpresas().getNome()));



            // Feche o documento
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
