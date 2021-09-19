package com.tarefas.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.tarefas.domain.Usuario;
import com.tarefas.utils.JpaUtil;

public class UsuarioDao {

	private EntityManager manager = null;
	
	// listar
	public List<Usuario> listar() {
		
		manager = JpaUtil.getEntityManagerFactory().createEntityManager();
		
		List<Usuario> usuarios = manager.createQuery("from Usuario", Usuario.class).getResultList();
		
		return usuarios;
	}
	
	// cadastrar
	public void cadastrar(Usuario u) throws Exception {
		
		manager = JpaUtil.getEntityManagerFactory().createEntityManager();
		
		try {
			manager.getTransaction().begin();
			manager.persist(u);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
            throw new Exception(e);
		} finally {
			manager.close();
		}
	}
	
	// buscar por id
	public Usuario buscarPorId(int id) {
		
		manager = JpaUtil.getEntityManagerFactory().createEntityManager();
		
		Usuario usuario = null;
		
		try {
			usuario = manager.find(Usuario.class, id);
		} catch (Exception e) {
			manager.close();
			e.printStackTrace();
		}
		
		return usuario;
	}
	
	// buscar por nome
	public List<Usuario> buscaPorNome(String campo) {
		
		manager = JpaUtil.getEntityManagerFactory().createEntityManager();
		
		TypedQuery<Usuario> query = manager.createQuery("from Usuario where upper(nome) like upper(:busca)", Usuario.class);
		query.setParameter("busca", "%" + campo + "%");
		
		return query.getResultList();
	}
	
	// atualizar
	public void atualizar(Usuario u) throws Exception {
		
		manager = JpaUtil.getEntityManagerFactory().createEntityManager();
		
		try {
			manager.getTransaction().begin();
			manager.merge(u);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
            throw new Exception(e);
		} finally {
			manager.close();
		}
	}
	
	// excluir
	public void excluir(Integer excluir) throws Exception {
		
		manager = JpaUtil.getEntityManagerFactory().createEntityManager();
		
		try {
			manager.getTransaction().begin();
			Usuario usuario = manager.find(Usuario.class, excluir);
			manager.remove(usuario);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
            throw new Exception(e);
		} finally {
			manager.close();
		}
	}
	
	// buscar usuario para login
	public Usuario efetuarLogin(Usuario usuario) {
		
		manager = JpaUtil.getEntityManagerFactory().createEntityManager();
		
		TypedQuery<Usuario> query = manager.createQuery("from Usuario "
				+ "where email = :email and senha = :senha", Usuario.class);
		query.setParameter("email", usuario.getEmail());
		query.setParameter("senha", usuario.getSenha());
		
		usuario = query.getSingleResult();
		
		if (usuario != null) {
			return usuario;
		} else {
			return null;
		}
	}
}
