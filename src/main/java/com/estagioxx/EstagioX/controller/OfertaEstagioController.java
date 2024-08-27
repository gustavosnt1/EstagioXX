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

    @GetMapping("/listar")
    public String listarOfertas(Model model, HttpSession session) {
        Long idAluno = (Long) session.getAttribute("idAluno");
        model.addAttribute("idAluno", idAluno);
        model.addAttribute("ofertas", ofertaEstagioService.findAll());
        return "ofertaestagio/lista"; // Nome do template para listar ofertas
    }


    @GetMapping("/cadastrar")
    public String cadastrar(@RequestParam("empresaId") Long empresaId, Model model) {
        model.addAttribute("idEmpresa", empresaId);
        model.addAttribute("ofertaEstagio", new OfertaEstagio());
        return "ofertaestagio/cadastro";
    }

/*    @PostMapping("/salvar")
    public String salvar(@ModelAttribute OfertaEstagio ofertaEstagio, @RequestParam("idEmpresa") Long idEmpresa) {
        ofertaEstagioService.save(ofertaEstagio, idEmpresa);
        return "redirect:/ofertaestagio/lista";
    }*/

    /*@GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("ofertas", ofertaEstagioService.findAll());
        return "ofertaestagio/lista";
    }*/

    @PostMapping("/candidatar")
    public String candidatar(@RequestParam Long idOfertaEstagio, HttpSession session) {
        Long idAluno = (Long) session.getAttribute("idAluno");
        Aluno aluno = alunoService.findById(idAluno);
        OfertaEstagio ofertaEstagio = ofertaEstagioService.findById(idOfertaEstagio);

        Candidatura candidatura = new Candidatura();
        candidatura.setAluno(aluno);
        candidatura.setOfertaEstagio(ofertaEstagio);
        candidatura.setStatus(Candidatura.StatusCandidatura.PENDENTE); // ou o status inicial desejado

        candidaturaService.save(candidatura);

        return "redirect:/ofertas"; // Redireciona para a lista de ofertas ou outra página apropriada
    }
}
