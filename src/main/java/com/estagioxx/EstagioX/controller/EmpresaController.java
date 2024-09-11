package com.estagioxx.EstagioX.controller;


import com.estagioxx.EstagioX.entities.*;
import com.estagioxx.EstagioX.repositories.CandidaturaRepository;
import com.estagioxx.EstagioX.services.AlunoService;
import com.estagioxx.EstagioX.services.EmpresaService;
import com.estagioxx.EstagioX.services.EstagioService;
import com.estagioxx.EstagioX.services.OfertaEstagioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping ("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private OfertaEstagioService ofertaEstagioService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private EstagioService estagioService;



    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView mav = new ModelAndView("empresa/criar-conta");
        mav.addObject("empresa", new Empresa());
        return mav;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute Empresa empresa) {
        empresaService.save(empresa);
        return new ModelAndView("redirect:/empresas/login");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("empresa/login");
    }

    @PostMapping("/autenticar")
    public ModelAndView autenticar(@ModelAttribute Empresa empresa) {
        boolean isAuthenticated = empresaService.authenticate(empresa.getEmail());

        if (isAuthenticated) {

            Empresa empresaAutenticado = empresaService.findByEmail(empresa.getEmail());


            httpSession.setAttribute("empresa", empresaAutenticado);
            return new ModelAndView("redirect:/empresas/dashboard");
        } else {
            ModelAndView mav = new ModelAndView("empresa/login");
            mav.addObject("error", "Email inválido");
            return mav;
        }
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {

        return new ModelAndView("empresa/dashboard");
    }

    @GetMapping("/criar-oferta")
    public ModelAndView criarOferta() {
        ModelAndView mav = new ModelAndView("empresa/criar-oferta");
        mav.addObject("ofertaEstagio", new OfertaEstagio());
        return mav;
    }

    @PostMapping("/salvar-oferta")
    public ModelAndView salvarOferta(@ModelAttribute OfertaEstagio ofertaEstagio) {
        Empresa empresa = (Empresa) httpSession.getAttribute("empresa");
        if (empresa != null) {
            ofertaEstagio.setEmpresas(empresa);
            ofertaEstagioService.save(ofertaEstagio);
        }
        return new ModelAndView("redirect:/empresas/dashboard");
    }

    @GetMapping("/listar-ofertas")
    public ModelAndView listarOfertas() {
        Empresa empresa = (Empresa) httpSession.getAttribute("empresa");
        if (empresa == null) {
            return new ModelAndView("redirect:/empresas/login");
        }
        List<OfertaEstagio> ofertas = ofertaEstagioService.findByEmpresa(empresa);
        ModelAndView mav = new ModelAndView("empresa/ofertas");
        mav.addObject("ofertas", ofertas);
        return mav;
    }

    @PostMapping("/deletar-oferta/{id}")
    public ModelAndView deletarOferta(@PathVariable Long id) {
        ofertaEstagioService.delete(id);
        return new ModelAndView("redirect:/empresas/listar-ofertas");
    }

    @GetMapping("/oferta/{id}/candidatos")
    public ModelAndView listarCandidatos(@PathVariable Long id) {
        List<Candidatura> candidaturas = empresaService.listarCandidatosPorOferta(id);
        ModelAndView mav = new ModelAndView("empresa/candidatos");
        mav.addObject("candidaturas", candidaturas);
        return mav;
    }

    @GetMapping("/fichaAluno/{id}")
    public ModelAndView verFichaAluno(@PathVariable Long id) {
        Aluno aluno = alunoService.findById(id);

        ModelAndView mav = new ModelAndView("empresa/ficha-aluno");
        mav.addObject("aluno", aluno);

        return mav;
    }

    @PostMapping("/aprovar-candidato/{id}")
    public ModelAndView aprovarCandidato(@PathVariable("id") Long candidaturaId) {
        try {
            empresaService.aprovarCandidato(candidaturaId);
            return new ModelAndView("redirect:/empresas/listar-ofertas").addObject("success", "Candidato aprovado com sucesso!");
        } catch (RuntimeException e) {
            return new ModelAndView("redirect:/empresas/listar-ofertas").addObject("error", "Erro ao aprovar candidato: " + e.getMessage());
        }
    }
}






