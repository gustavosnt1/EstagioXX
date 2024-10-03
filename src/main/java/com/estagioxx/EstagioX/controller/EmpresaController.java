package com.estagioxx.EstagioX.controller;

import com.estagioxx.EstagioX.entities.Aluno;
import com.estagioxx.EstagioX.entities.Candidatura;
import com.estagioxx.EstagioX.entities.Empresa;
import com.estagioxx.EstagioX.entities.Estagio;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
import com.estagioxx.EstagioX.repositories.CandidaturaRepository;
import com.estagioxx.EstagioX.repositories.OfertaEstagioRepository;
import com.estagioxx.EstagioX.services.AlunoService;
import com.estagioxx.EstagioX.services.EmpresaService;
import com.estagioxx.EstagioX.services.EstagioService;
import com.estagioxx.EstagioX.services.OfertaEstagioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empresas")
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

    @Autowired
    private OfertaEstagioRepository ofertaEstagioRepository;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView mav = new ModelAndView("empresa/criar-conta");
        mav.addObject("empresa", new Empresa());
        return mav;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid @ModelAttribute Empresa empresa, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("empresa/criar-conta");
            mav.addObject("empresa", empresa);
            return mav;
        }
        empresaService.save(empresa);
        return new ModelAndView("redirect:/empresas/login");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("empresa/login");
    }

    @PostMapping("/autenticar")
    public ModelAndView autenticar(@ModelAttribute Empresa empresa) {
        boolean isAuthenticated = empresaService.authenticate(empresa.getEmail(), empresa.getPassword());

        if (isAuthenticated) {
            Empresa empresaAutenticada = empresaService.findByEmail(empresa.getEmail());
            httpSession.setAttribute("empresa", empresaAutenticada);
            return new ModelAndView("redirect:/empresas/dashboard");
        } else {
            ModelAndView mav = new ModelAndView("empresa/login");
            mav.addObject("error", "Email inválido");
            return mav;
        }
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        Empresa empresa = (Empresa) httpSession.getAttribute("empresa");

        if (empresa == null) {
            return new ModelAndView("redirect:/empresas/login");
        }

        ModelAndView mav = new ModelAndView("empresa/dashboard");
        mav.addObject("nomeEmpresa", empresa.getNome());  // Adiciona o nome do aluno ao modelo
        return mav;
    }

    @GetMapping("/criar-oferta")
    public ModelAndView criarOferta() {
        ModelAndView mav = new ModelAndView("empresa/criar-oferta");
        mav.addObject("ofertaEstagio", new OfertaEstagio());
        return mav;
    }

    @PostMapping("/salvar-oferta")
    public ModelAndView salvarOferta(@Valid @ModelAttribute OfertaEstagio ofertaEstagio, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("empresa/criar-oferta");
            mav.addObject("ofertaEstagio", ofertaEstagio);
            return mav;
        }

        Empresa empresa = (Empresa) httpSession.getAttribute("empresa");
        if (empresa != null) {
            ofertaEstagio.setEmpresas(empresa);
            ofertaEstagioService.save(ofertaEstagio);
        }
        return new ModelAndView("redirect:/empresas/dashboard");
    }

    @GetMapping("/listar-ofertas")
    public ModelAndView listarOfertas(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int size) {
        Empresa empresa = (Empresa) httpSession.getAttribute("empresa");
        if (empresa == null) {
            return new ModelAndView("redirect:/empresas/login");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<OfertaEstagio> ofertas = ofertaEstagioService.findByEmpresaWithPagination(empresa, pageable);

        ModelAndView mav = new ModelAndView("empresa/ofertas");
        mav.addObject("ofertas", ofertas);
        mav.addObject("nomeEmpresa", empresa.getNome());
        return mav;
    }

    @PostMapping("/deletar-oferta/{id}")
    public ModelAndView deletarOferta(@PathVariable Long id) {
        ofertaEstagioService.delete(id);
        return new ModelAndView("redirect:/empresas/listar-ofertas");
    }

    @GetMapping("/fichaAluno/{id}")
    public ModelAndView verFichaAluno(@PathVariable Long id) {
        Aluno aluno = alunoService.findById(id);
        ModelAndView mav = new ModelAndView("empresa/ficha-aluno");
        mav.addObject("aluno", aluno);
        return mav;
    }

    @GetMapping("/oferta/{id}/candidatos")
    public ModelAndView listarCandidatos(@PathVariable Long id) {
        Empresa empresa = (Empresa) httpSession.getAttribute("empresa");
        if (empresa == null) {
            return new ModelAndView("redirect:/empresas/login");
        }

        List<Candidatura> candidaturas = empresaService.listarCandidatosPorOferta(id);
        OfertaEstagio ofertaEstagio = ofertaEstagioService.findById(id);

        ModelAndView mav = new ModelAndView("empresa/candidatos");
        mav.addObject("candidaturas", candidaturas);
        mav.addObject("ofertaEstagio", ofertaEstagio);
        return mav;
    }

    @PostMapping("/confirmar-estagio/{candidaturaId}")
    public ModelAndView confirmarEstagio(
            @PathVariable Long candidaturaId,
            @RequestParam("dataInicio") LocalDate dataInicio,
            @RequestParam("dataTermino") LocalDate dataTermino,
            @RequestParam("valorEstagio") double valorEstagio) {

        Candidatura candidatura = candidaturaRepository.findById(candidaturaId).orElse(null);
        if (candidatura != null) {
            candidatura.setStatus(Candidatura.StatusCandidatura.ACEITA);
            candidaturaRepository.save(candidatura);

            Estagio estagio = new Estagio();
            estagio.setAluno(candidatura.getAluno());
            estagio.setOfertaEstagio(candidatura.getOfertaEstagio());
            estagio.setDataInicio(dataInicio);
            estagio.setDataTermino(dataTermino);
            estagio.setValorEstagio(valorEstagio);

            byte[] termoEstagio = estagioService.gerarTermoEstagio(estagio);
            estagio.setTermoEstagio(termoEstagio);
            estagioService.save(estagio);

            estagioService.save(estagio);

            OfertaEstagio ofertaEstagio = candidatura.getOfertaEstagio();
            if (ofertaEstagio != null) {
                ofertaEstagio.setPreenchida(true);
                ofertaEstagioRepository.save(ofertaEstagio);
            }
        }

        return new ModelAndView("redirect:/empresas/listar-ofertas");
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        httpSession.invalidate();
        return new ModelAndView("redirect:/home");
    }
}