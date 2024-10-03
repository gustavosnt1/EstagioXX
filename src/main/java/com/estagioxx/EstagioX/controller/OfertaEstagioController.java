package com.estagioxx.EstagioX.controller;

import com.estagioxx.EstagioX.entities.Aluno;
import com.estagioxx.EstagioX.entities.Candidatura;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
import com.estagioxx.EstagioX.services.AlunoService;
import com.estagioxx.EstagioX.services.CandidaturaService;
import com.estagioxx.EstagioX.services.OfertaEstagioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ofertas")
public class OfertaEstagioController {

    @Autowired
    private OfertaEstagioService ofertaEstagioService;

    @Autowired
    private CandidaturaService candidaturaService;

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/candidatar")
    public String candidatar(@RequestParam Long idOfertaEstagio, HttpSession session) {
        Long idAluno = (Long) session.getAttribute("idAluno");
        Aluno aluno = alunoService.findById(idAluno);
        OfertaEstagio ofertaEstagio = ofertaEstagioService.findById(idOfertaEstagio);

        Candidatura candidatura = new Candidatura();
        candidatura.setAluno(aluno);
        candidatura.setOfertaEstagio(ofertaEstagio);
        candidatura.setStatus(Candidatura.StatusCandidatura.PENDENTE);

        candidaturaService.save(candidatura);

        return "redirect:/ofertas";
    }
}
