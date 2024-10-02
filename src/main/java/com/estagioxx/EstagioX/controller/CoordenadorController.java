package com.estagioxx.EstagioX.controller;


import com.estagioxx.EstagioX.entities.*;
import com.estagioxx.EstagioX.repositories.EmpresaRepository;
import com.estagioxx.EstagioX.services.CoordenadorService;
import com.estagioxx.EstagioX.services.EmpresaService;
import com.estagioxx.EstagioX.services.EstagioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
    private EstagioService estagioService;

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView mav = new ModelAndView("coordenador/criar-conta");
        mav.addObject("coordenador", new Coordenador());
        return mav;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid @ModelAttribute Coordenador coordenador, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("coordenador/criar-conta");
            mav.addObject("coordenador", coordenador);
            return mav;
        }
        coordenadorService.save(coordenador);
        return new ModelAndView("redirect:/coordenadores/login");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("coordenador/login");
    }

    @PostMapping("/autenticar")
    public ModelAndView autenticar(@ModelAttribute Coordenador coordenador) {
        boolean isAuthenticated = coordenadorService.authenticate(coordenador.getUsername(), coordenador.getPassword());

        if (isAuthenticated) {

            Coordenador coordenadorAutenticado = coordenadorService.findByUsername(coordenador.getUsername());
            System.out.println("Coordenador autenticado: " + coordenadorAutenticado);


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
        Coordenador coordenador = (Coordenador) httpSession.getAttribute("coordenador");

        List<OfertaEstagio> ofertas = coordenadorService.listarTodasOfertas();
        ModelAndView mav = new ModelAndView("coordenador/dashboard");
        mav.addObject("ofertas", ofertas);
        mav.addObject("nomeCoordenador", coordenador.getNome());
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
    public ModelAndView listarEmpresas(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "2") int size) {
        Coordenador coordenador = (Coordenador) httpSession.getAttribute("coordenador");

        Pageable pageable = PageRequest.of(page, size);
        Page<Empresa> empresasPage = empresaService.findAll(pageable);

        ModelAndView mav = new ModelAndView("coordenador/listagem-empresa");
        mav.addObject("empresas", empresasPage.getContent());
        mav.addObject("nomeCoordenador", coordenador.getNome());
        mav.addObject("empresasPage", empresasPage); // Adiciona a página de empresas para a view

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
                                  @Valid @ModelAttribute("empresa") Empresa empresa,
                                  BindingResult bindingResult,
                                  @RequestParam(value = "pdfEmpresa", required = false) MultipartFile pdfEmpresa) throws IOException {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("coordenador/editar-empresa");
            mav.addObject("empresa", empresa);
            return mav;
        }

        if (pdfEmpresa != null && !pdfEmpresa.isEmpty()) {
            empresa.setPdfEmpresa(pdfEmpresa.getBytes());
        }

        empresaService.update(id, empresa);

        return new ModelAndView("redirect:/coordenadores/dashboard");
    }

    @GetMapping("/listar-estagios")
    public ModelAndView listarEstagios( @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size) {
        Coordenador coordenador = (Coordenador) httpSession.getAttribute("coordenador");

        Page<Estagio> estagiosPage = coordenadorService.listarEstagios(PageRequest.of(page, size));
        ModelAndView mav = new ModelAndView("coordenador/listagem-estagio");

        mav.addObject("estagios", estagiosPage.getContent());
        mav.addObject("totalPages", estagiosPage.getTotalPages());
        mav.addObject("currentPage", page);
        mav.addObject("nomeCoordenador", coordenador.getNome());

        return mav;
    }

    @GetMapping("/baixar-termo-estagio/{estagioId}")
    public ResponseEntity<byte[]> baixarTermoEstagio(@PathVariable Long estagioId) {
        Estagio estagio = estagioService.findById(estagioId);
        System.out.println(estagioId);
        if (estagio == null || estagio.getTermoEstagio() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] termoEstagio = estagio.getTermoEstagio();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("termo_estagio_" + estagioId + ".pdf").build());

        return new ResponseEntity<>(termoEstagio, headers, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        httpSession.invalidate();
        return new ModelAndView("redirect:/home");
    }
}