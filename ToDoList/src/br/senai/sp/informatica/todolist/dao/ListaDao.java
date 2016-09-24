package br.senai.sp.informatica.todolist.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.informatica.todolist.modelo.ItemLista;
import br.senai.sp.informatica.todolist.modelo.Lista;

@Repository
@Transactional
public class ListaDAO {
	// @PersistenceContext serve para não estanciar o EntityManager
	@PersistenceContext
	private EntityManager manager;
	
	public void inserir(Lista lista){
		manager.persist(lista);	
	}
	
	// Métodos que não inserem dados, não é necessário inserir o transactional
	public List<Lista> Listar(){	
		/*
		 * JPL
		 *lista = resultado do que foi buscado pelo Lista
		 * Lista = Nome da classe onde está a lista, e na frente o apelido, este tem que ser igual a resposta
		 * 
		 * Usando assim, não é necessário fazer Inner Join, pois ele faz tudo automático, mas também pode se fazer Organização
		 * O Lista.class faz com que o que vai pegar tem que ser convertido para o formato da classe Lista
		 */
		TypedQuery<Lista> query = manager.createQuery("SELECT l FROM Lista l", Lista.class);
		return query.getResultList();
	}

	@Transactional
	public void ExcluirLista(Long idLista){
		// Sincroniza o objeto no banco de dados, buscando pelo ID, depois disso, ele exclui
		Lista lista = manager.find(Lista.class, idLista);
		manager.remove(lista);
	}

	public void ExcluirItem(long idItem){
		ItemLista item = manager.find(ItemLista.class, idItem);
		Lista lista = item.getLista();
		lista.getItens().remove(item);
		manager.merge(lista);
	}
}