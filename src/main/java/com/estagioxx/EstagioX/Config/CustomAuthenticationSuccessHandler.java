package com.estagioxx.EstagioX.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            return;
        }
        System.out.println("Redirecionando para: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        if (authentication == null) {
            return "/home";
        }

        System.out.println("Verificando papéis do usuário autenticado...");
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ALUNO"))) {
            return "/alunos/dashboard";
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_EMPRESA"))) {
            return "/empresas/dashboard";
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_COORDENADOR"))) {
            return "/coordenadores/dashboard";
        }

        return "/home";
    }
}
