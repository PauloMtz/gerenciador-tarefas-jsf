package com.tarefas.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.tarefas.dao.UsuarioDao;
import com.tarefas.domain.Usuario;
import com.tarefas.utils.EncryptUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	private Usuario usuario;
	private UsuarioDao dao;
	private List<Usuario> usuarios;
	
	private FacesContext facesContext;
	
	private Integer idUsuario;
	private Integer excluirUsuario;
	
	@PostConstruct
	public void init() {
		usuario = new Usuario();
		dao = new UsuarioDao();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Usuario> getUsuarios() {
		
		if (usuarios == null) {
			usuarios = dao.listar();
		}
		
		return usuarios;
	}
	
	public String gravar() {
		
		facesContext = FacesContext.getCurrentInstance();
		EncryptUtil app = new EncryptUtil();
		
		try {
			String hashSenha = app.encode(usuario.getSenha());
			usuario.setSenha(hashSenha);
			if (idUsuario == null) {
				dao.cadastrar(usuario);
				facesContext.addMessage(null, new FacesMessage("Registro inserido com sucesso!"));
			} else {
				dao.atualizar(usuario);
				facesContext.addMessage(null, new FacesMessage("Registro atualizado com sucesso!"));
			}
		} catch (Exception e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		return "/admin/usuarios/lista?faces-redirect=true";
	}
	
	public void carregar() {
		if (idUsuario != null) {
			usuario = dao.buscarPorId(idUsuario);
		}
	}
	
	public String remover() {
		
		facesContext = FacesContext.getCurrentInstance();
		String retornoUsuario = null;
		
		if (excluirUsuario != null) {
			try {
				dao.excluir(excluirUsuario);
				excluirUsuario = null;
				facesContext.addMessage(null, new FacesMessage("Registro excluído com sucesso!"));
				retornoUsuario = "/admin/usuarios/lista?faces-redirect=true";
			} catch (Exception e) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
			}
		}
		
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		return retornoUsuario;
	}
	
	public Integer getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public Integer getExcluirUsuario() {
		return excluirUsuario;
	}
	
	public void setExcluirUsuario(Integer excluirUsuario) {
		this.excluirUsuario = excluirUsuario;
	}
}
