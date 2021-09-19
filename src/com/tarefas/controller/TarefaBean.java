package com.tarefas.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.tarefas.dao.TarefaDao;
import com.tarefas.domain.Tarefa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TarefaBean implements Serializable {

	private Tarefa tarefa;
	private TarefaDao dao;
	private List<Tarefa> tarefas;
	
	private Integer editar;
	private Integer excluir;
	private Integer concluir;
	
	private FacesContext facesContext;
	
	@PostConstruct
	public void init() {
		dao = new TarefaDao();
		tarefa = new Tarefa();
	}
	
	// listar
	public List<Tarefa> getTarefas() {
		
		if (tarefas == null) {
			tarefas = dao.listar();
		}
		
		return tarefas;
	}
	
	// carrega o formulário para edição
	public void carregar() {
		
		if (editar != null) {
			tarefa = dao.buscarPorId(editar);
		}
	}
	
	// inserir ou atualizar
	public String salvar() {
		
		facesContext = FacesContext.getCurrentInstance();
		
		try {
			if (editar == null) {
				if (tarefa.getDataConclusao().before(new Date())) {
					facesContext.addMessage(null, 
							new FacesMessage("A data de expiração deve ser maior que a data de hoje."));
					return null;
				}
				dao.cadastrar(tarefa);
				facesContext.addMessage(null, new FacesMessage("Registro inserido com sucesso!"));
			} else {
				dao.atualizar(tarefa);
				facesContext.addMessage(null, new FacesMessage("Registro atualizado com sucesso!"));
			}
			
			tarefas = dao.listar();
		} catch (Exception e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					e.getMessage(), e.getMessage()));
		}
		
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		return "/admin/tarefas/lista?faces-redirect=true";
	}
	
	// excluir
	public String remover() {
		
		facesContext = FacesContext.getCurrentInstance();
		String retornoTarefa = null;
		
		if (excluir != null) {
			try {
				dao.excluir(excluir);
				excluir = null;
				facesContext.addMessage(null, new FacesMessage("Registro excluído com sucesso!"));
				retornoTarefa = "/admin/tarefas/lista?faces-redirect=true";
			} catch (Exception e) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						e.getMessage(), e.getMessage()));
			}
		}
		
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		return retornoTarefa;
	}
	
	// concluir tarefa
	public String concluirTarefa() {
		
		facesContext = FacesContext.getCurrentInstance();
		String retornoTarefa = null;
		
		if (concluir != null) {
			tarefa = dao.buscarPorId(concluir);
			tarefa.setConcluida(true);
			
			try {
				dao.atualizar(tarefa);
				concluir = null;
				facesContext.addMessage(null, new FacesMessage("Tarefa concluída com sucesso!"));
				retornoTarefa = "/admin/tarefas/lista?faces-redirect=true";
			} catch (Exception e) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						e.getMessage(), e.getMessage()));
			}
		}
		
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		return retornoTarefa;
	}
	
	// getters e setters
	public Tarefa getTarefa() {
		return tarefa;
	}

	public Integer getEditar() {
		return editar;
	}

	public void setEditar(Integer editar) {
		this.editar = editar;
	}

	public Integer getExcluir() {
		return excluir;
	}

	public void setExcluir(Integer excluir) {
		this.excluir = excluir;
	}
	
	public Integer getConcluir() {
		return concluir;
	}
	
	public void setConcluir(Integer concluir) {
		this.concluir = concluir;
	}
}
