package br.senai.sp.informatica.todolist.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.todolist.modelo.Lista;

@Repository
public class ListaDao {
	// @PersistenceContext serve para nao estanciar o EntityManager
	@PersistenceContext
	private EntityManager maneger;
	
	public void inserir(Lista lista){
		maneger.persist(lista);
		
	}
	
	
	
}
