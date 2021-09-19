package com.tarefas.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.tarefas.domain.Tarefa;
import com.tarefas.utils.JpaUtil;

public class TarefaDao {

	private EntityManager manager = null;
	
	// listar
	public List<Tarefa> listar() {
		
		manager = JpaUtil.getEntityManagerFactory().createEntityManager();
		
		List<Tarefa> tarefas = manager.createQuery("from Tarefa order by id_tarefa desc", Tarefa.class).getResultList();
		
		return tarefas;
	}
	
	// cadastrar
	public void cadastrar(Tarefa p) throws Exception {
		
		manager = JpaUtil.getEntityManagerFactory().createEntityManager();
		
		try {
			manager.getTransaction().begin();
			manager.persist(p);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
            throw new Exception(e);
		} finally {
			manager.close();
		}
	}
	
	// buscar por id
	public Tarefa buscarPorId(int id) {
		
		manager = JpaUtil.getEntityManagerFactory().createEntityManager();
		
		Tarefa tarefa = null;
		
		try {
			tarefa = manager.find(Tarefa.class, id);
		} catch (Exception e) {
			manager.close();
			e.printStackTrace();
		}
		
		return tarefa;
	}
	
	// buscar por nome ou e-mail
	public List<Tarefa> buscaPorNome(String campo) {
		
		manager = JpaUtil.getEntityManagerFactory().createEntityManager();
		
		TypedQuery<Tarefa> query = manager.createQuery("from Tarefa where upper(tarefa) like upper(:busca)", Tarefa.class);
		query.setParameter("busca", "%" + campo + "%");
		
		return query.getResultList();
	}
	
	// atualizar
	public void atualizar(Tarefa t) throws Exception {
		
		manager = JpaUtil.getEntityManagerFactory().createEntityManager();
		
		try {
			manager.getTransaction().begin();
			manager.merge(t);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
            throw new Exception(e);
		} finally {
			manager.close();
		}
	}
	
	// excluir
	public void excluir(Integer id) throws Exception {
		
		manager = JpaUtil.getEntityManagerFactory().createEntityManager();
		
		try {
			manager.getTransaction().begin();
			Tarefa tarefa = manager.find(Tarefa.class, id);
			manager.remove(tarefa);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
            throw new Exception(e);
		} finally {
			manager.close();
		}
	}
}
