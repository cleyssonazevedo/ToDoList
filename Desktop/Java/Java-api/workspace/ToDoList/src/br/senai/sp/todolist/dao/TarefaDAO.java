package br.senai.sp.todolist.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.todolist.model.Tarefa;

@Repository
public class TarefaDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public void inserir(Tarefa tarefa){
		manager.persist(tarefa);
	}
	
	

}
