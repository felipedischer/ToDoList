package br.senai.sp.informatica.todolist.dao;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.informatica.todolist.modelo.Usuario;

@Repository
public class UsuarioDao {

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public void inserir(Usuario usuario) {
		manager.persist(usuario);
	}
	
	//@Transactional
		public Usuario obter(Long idUsuario){
			return manager.find(Usuario.class, idUsuario);
		}
	
	public Usuario logar(Usuario usuario){
		TypedQuery<Usuario> query = manager.createQuery
				("select u from Usuario u where u.login = :login and u.senha = :senha ", Usuario.class);
		query.setParameter("login", usuario.getLogin());
		query.setParameter("senha", usuario.getSenha());
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
		
		
}
