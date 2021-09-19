package com.tarefas.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/admin/*"})
public class AutorizacaoFilter implements Filter {
	
	// esse filtro deve ser declarado no arquivo web.xml
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
 
        boolean isLoggedIn = (session != null && session.getAttribute("usuario_logado") != null);
        String loginURI = httpRequest.getContextPath() + "/login.xhtml";
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.xhtml");
 
        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
        	httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/*");
        } else if (isLoggedIn || isLoginRequest) {
            chain.doFilter(request, response);
        } else {
        	httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.xhtml");
        }
    }
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}
}
