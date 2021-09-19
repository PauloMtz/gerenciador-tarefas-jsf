package com.tarefas.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.tarefas.dao.UsuarioDao;
import com.tarefas.domain.Usuario;
import com.tarefas.utils.EncryptUtil;
import com.tarefas.utils.SessionUtils;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private Usuario usuario;
	private UsuarioDao dao;
	
	private FacesContext facesContext;
	
	@PostConstruct
	public void init() {
		dao = new UsuarioDao();
		usuario = new Usuario();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	// login
	// Fonte: https://www.journaldev.com/7252/jsf-authentication-login-logout-database-example
	public String entrar() {
		
		facesContext = FacesContext.getCurrentInstance();
		EncryptUtil app = new EncryptUtil();
		
		try {
			String hashSenha = app.encode(usuario.getSenha());
			usuario.setSenha(hashSenha);
			usuario = dao.efetuarLogin(usuario);
			
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("usuario_logado", usuario.getNome());
			session.setAttribute("id_usuario_logado", usuario.getId());
			
			return "/admin/tarefas/lista.xhtml?faces-redirect=true";
		} catch (Exception e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Não foi possível efetuar o login", e.getMessage()));
			facesContext.getExternalContext().getFlash().setKeepMessages(true);
			return "login";
		}
	}
	
	// logout
	public String sair() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "/login.xhtml?faces-redirect=true";
	}
	
	public boolean estaLogado() {
	    return usuario != null;
	  }
}
