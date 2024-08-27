package com.estagioxx.EstagioX.controller;

import com.estagioxx.EstagioX.entities.Aluno;
import com.estagioxx.EstagioX.entities.Empresa;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
import com.estagioxx.EstagioX.services.AlunoService;
import com.estagioxx.EstagioX.services.EmpresaService;
import com.estagioxx.EstagioX.services.OfertaEstagioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView mav = new ModelAndView("empresa/criar-conta");  // Nome da página Thymeleaf criar-conta.html
        mav.addObject("empresa", new Empresa());
        return mav;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute Empresa empresa) {
        empresaService.save(empresa);
        return new ModelAndView("redirect:/empresas/login");  // Redireciona para a página de login após salvar
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("empresa/login");  // Nome da página Thymeleaf login.html
    }

    @PostMapping("/autenticar")
    public ModelAndView autenticar(@ModelAttribute Empresa empresa) {
        boolean isAuthenticated = empresaService.authenticate(empresa.getEmail());

        if (isAuthenticated) {
            // Opcional: Carregar informações adicionais da empresa, se necessário
            Empresa empresaAutenticado = empresaService.findByEmail(empresa.getEmail());

            // Armazenar a empresa na sessão
            httpSession.setAttribute("empresa", empresaAutenticado);
            return new ModelAndView("redirect:/empresas/dashboard");  // Redireciona para a página inicial se o login for bem-sucedido
        } else {
            ModelAndView mav = new ModelAndView("empresa/login");
            mav.addObject("error", "Email inválido");
            return mav;
        }
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        // Opcional: Carregar dados necessários para o dashboard
        return new ModelAndView("empresa/dashboard");  // Nome da página Thymeleaf dashboard.html
    }

    @GetMapping("/criar-oferta")
    public ModelAndView criarOferta() {
        ModelAndView mav = new ModelAndView("empresa/criar-oferta");  // Nome da página Thymeleaf criar-oferta.html
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
        return new ModelAndView("redirect:/empresas/dashboard");  // Redireciona para o dashboard após salvar a oferta
    }

    @GetMapping("/listar-ofertas")
    public ModelAndView listarOfertas() {
        Empresa empresa = (Empresa) httpSession.getAttribute("empresa");
        if (empresa == null) {
            return new ModelAndView("redirect:/empresas/login");  // Redireciona para o login se a empresa não estiver autenticada
        }
        List<OfertaEstagio> ofertas = ofertaEstagioService.findByEmpresa(empresa);
        ModelAndView mav = new ModelAndView("empresa/ofertas");  // Nome da página Thymeleaf ofertas.html
        mav.addObject("ofertas", ofertas);
        return mav;
    }
}






