package br.senai.sp.informatica.todolist.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.informatica.todolist.modelo.ItemLista;
import br.senai.sp.informatica.todolist.modelo.Lista;

@Repository
public class ItemDao {

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	public void marcarFeito(Long idItem, boolean valor) {
		ItemLista item = manager.find(ItemLista.class, idItem);
		item.setFeito(valor);
		manager.merge(item);
	}
	
	@Transactional
	public void inserir(Long idLista, ItemLista item){
		item.setLista(manager.find(Lista.class,idLista));
		manager.persist(item);
	}

	//@Transactional
	public ItemLista obter(Long idItem){
		return manager.find(ItemLista.class, idItem);
	}
	
}