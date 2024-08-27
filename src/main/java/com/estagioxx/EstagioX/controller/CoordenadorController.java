package com.estagioxx.EstagioX.controller;

import com.estagioxx.EstagioX.entities.Aluno;
import com.estagioxx.EstagioX.entities.Candidatura;
import com.estagioxx.EstagioX.entities.Coordenador;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
import com.estagioxx.EstagioX.services.CoordenadorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/coordenadores")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView mav = new ModelAndView("coordenador/criar-conta");  // Nome da página Thymeleaf criar-conta.html
        mav.addObject("coordenador", new Coordenador());
        return mav;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute Coordenador coordenador) {
        coordenadorService.save(coordenador);
        return new ModelAndView("redirect:/coordenadores/login");  // Redireciona para a página de login após salvar
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("coordenador/login");  // Nome da página Thymeleaf login.html
    }

    @PostMapping("/autenticar")
    public ModelAndView autenticar(@ModelAttribute Coordenador coordenador) {
        Coordenador coordenadorAutenticado = coordenadorService.authenticate(coordenador.getUsername(), coordenador.getPassword());

        if (coordenadorAutenticado != null) {
            httpSession.setAttribute("coordenador", coordenadorAutenticado);
            return new ModelAndView("redirect:/coordenadores/dashboard");
        } else {
            ModelAndView mav = new ModelAndView("coordenador/login");
            mav.addObject("error", "Usuário ou senha inválidos");
            return mav;
        }
    }

    @GetMapping("/dashboard")
    public ModelAndView mostrarDashboard() {
        List<OfertaEstagio> ofertas = coordenadorService.listarTodasOfertas();
        ModelAndView mav = new ModelAndView("coordenador/dashboard");
        mav.addObject("ofertas", ofertas);
        return mav;
    }

    @GetMapping("/oferta/{id}/candidatos")
    public ModelAndView listarCandidatos(@PathVariable Long id) {
        List<Candidatura> candidaturas = coordenadorService.listarCandidatosPorOferta(id);
        ModelAndView mav = new ModelAndView("coordenador/candidatos");
        mav.addObject("candidaturas", candidaturas);
        return mav;
    }
}