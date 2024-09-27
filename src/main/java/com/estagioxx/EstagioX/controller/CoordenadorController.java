package com.estagioxx.EstagioX.controller;


import com.estagioxx.EstagioX.entities.Candidatura;
import com.estagioxx.EstagioX.entities.Coordenador;
import com.estagioxx.EstagioX.entities.Empresa;
import com.estagioxx.EstagioX.entities.OfertaEstagio;
import com.estagioxx.EstagioX.services.CoordenadorService;
import com.estagioxx.EstagioX.services.EmpresaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/coordenadores")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView mav = new ModelAndView("coordenador/criar-conta");
        mav.addObject("coordenador", new Coordenador());
        return mav;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid @ModelAttribute Coordenador coordenador) {
        coordenadorService.save(coordenador);
        return new ModelAndView("redirect:/coordenadores/login");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("coordenador/login");
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

    @GetMapping("/listar-empresas")
    public ModelAndView listarEmpresas() {
        List<Empresa> empresas = empresaService.findAll();
        ModelAndView mav = new ModelAndView("coordenador/listagem-empresa");
        mav.addObject("empresas", empresas);
        return mav;
    }

    @GetMapping("/empresas/editar/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        Empresa empresa = empresaService.findById(id);
        ModelAndView mav = new ModelAndView("coordenador/editar-empresa");
        mav.addObject("empresa", empresa);
        return mav;
    }

    @PostMapping("/empresas/atualizar")
    public ModelAndView atualizar(@RequestParam("idEmpresa") Long id,
                                  @Valid @ModelAttribute Empresa empresa,
                                  @RequestParam(value = "pdfEmpresa", required = false) MultipartFile pdfEmpresa) throws IOException {
        if (pdfEmpresa != null && !pdfEmpresa.isEmpty()) {
            empresa.setPdfEmpresa(pdfEmpresa.getBytes());
        }
        empresaService.update(id, empresa);
        return new ModelAndView("redirect:/coordenadores/dashboard");
    }
}