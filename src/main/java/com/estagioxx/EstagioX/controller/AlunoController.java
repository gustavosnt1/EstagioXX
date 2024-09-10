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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping ("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private CandidaturaService candidaturaService;

    @Autowired
    private OfertaEstagioService ofertaEstagioService;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView mav = new ModelAndView("aluno/criar-conta");
        List<String> habilidadesPredefinidas = Arrays.asList("Programação Python", "Design em CSS", "Especialista em UX", "Programador de Testes");
        mav.addObject("habilidades", habilidadesPredefinidas);
        mav.addObject("aluno", new Aluno());
        return mav;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute Aluno aluno) {
        alunoService.save(aluno);
        return new ModelAndView("redirect:/alunos/login");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("aluno/login");
    }

    @PostMapping("/autenticar")
    public ModelAndView autenticar(@ModelAttribute Aluno aluno) {
        boolean isAuthenticated = alunoService.authenticate(aluno.getUsername(), aluno.getPassword());


        if (isAuthenticated) {

            Aluno alunoAutenticado = alunoService.findByUsername(aluno.getUsername());
            System.out.println("Aluno autenticado: " + alunoAutenticado);

            // Armazenar o aluno na sessão
            httpSession.setAttribute("aluno", alunoAutenticado);
            return new ModelAndView("redirect:/alunos/dashboard");
        } else {
            ModelAndView mav = new ModelAndView("aluno/login");
            mav.addObject("error", "Usuário ou senha inválidos");
            return mav;
        }
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        return new ModelAndView("aluno/dashboard");
    }


    @GetMapping("/ofertas")
    public ModelAndView listarOfertas(@RequestParam(value = "query", required = false) String query) {
        Aluno aluno = (Aluno) httpSession.getAttribute("aluno");

        if (aluno == null) {
            return new ModelAndView("redirect:/alunos/login");
        }

        List<OfertaEstagio> ofertasDisponiveis;

        if (query != null && !query.isEmpty()) {
            ofertasDisponiveis = ofertaEstagioService.search(query, aluno.getIdAluno());
        } else {

            List<Candidatura> candidaturas = candidaturaService.listarCandidaturasPorAluno(aluno.getIdAluno());

            Set<Long> ofertasCandidatas = candidaturas.stream()
                    .map(c -> c.getOfertaEstagio().getIdOfertaEstagio())
                    .collect(Collectors.toSet());

            ofertasDisponiveis = ofertaEstagioService.findAll().stream()
                    .filter(o -> !ofertasCandidatas.contains(o.getIdOfertaEstagio()))
                    .collect(Collectors.toList());
        }

        ModelAndView mav = new ModelAndView("aluno/listar-ofertas");
        mav.addObject("ofertas", ofertasDisponiveis);
        mav.addObject("query", query);
        return mav;
    }

    @GetMapping("/candidatar")
    public ModelAndView candidatar(@RequestParam Long ofertaId) {
        Aluno aluno = (Aluno) httpSession.getAttribute("aluno");

        if (aluno == null) {
            return new ModelAndView("redirect:/alunos/login");
        }

        OfertaEstagio ofertaEstagio = ofertaEstagioService.findById(ofertaId);
        if (ofertaEstagio == null) {
            return new ModelAndView("redirect:/alunos/ofertas");
        }

        Candidatura candidatura = new Candidatura();
        candidatura.setAluno(aluno);
        candidatura.setOfertaEstagio(ofertaEstagio);
        candidatura.setStatus(Candidatura.StatusCandidatura.PENDENTE);

        candidaturaService.save(candidatura);

        return new ModelAndView("redirect:/alunos/ofertas-inscritas");
    }

    @GetMapping("/ofertas-inscritas")
    public ModelAndView listarOfertasInscritas() {
        Aluno aluno = (Aluno) httpSession.getAttribute("aluno");

        if (aluno == null) {
            return new ModelAndView("redirect:/alunos/login");
        }

        List<Candidatura> candidaturas = candidaturaService.listarCandidaturasPorAluno(aluno.getIdAluno());
        System.out.println("Candidaturas: " + candidaturas);

        for (Candidatura c : candidaturas) {
            System.out.println("Candidatura Status: " + c.getStatus());
        }

        ModelAndView mav = new ModelAndView("aluno/ofertas-inscritas");
        mav.addObject("candidaturas", candidaturas);
        return mav;
    }

}
