package com.tarefas.utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {
	
	// Fonte: https://www.journaldev.com/7252/jsf-authentication-login-logout-database-example

	public static HttpSession getSession() {
		
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public static String getUserName() {
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		
		return session.getAttribute("usuario_logado").toString();
	}

	public static String getUserId() {
		
		HttpSession session = getSession();
		
		if (session != null) {
			return (String) session.getAttribute("id_usuario_logado");
		} else {
			return null;
		}
	}
}
