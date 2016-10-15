package br.senai.sp.todolist.controller;

import java.net.URI;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.senai.sp.todolist.dao.TarefaDAO;
import br.senai.sp.todolist.model.ItemTarefa;
import br.senai.sp.todolist.model.Tarefa;

@RestController
public class TarefaRestController {
	
	@Autowired
	private TarefaDAO dao;
	
	public ResponseEntity<Object> salvar(@RequestBody Tarefa tarefa){
		try {
			for(ItemTarefa item: tarefa.getItens()){
				item.setTarefa(tarefa);
			}
			dao.inserir(tarefa);
			return ResponseEntity.created(new URI ("/lista/"+tarefa.getId())).body(tarefa);
		}catch(ConstraintViolationException e){
			String mensagem = "";
			for(ConstraintViolation<?> constrain: e.getConstraintViolations()){
				mensagem +=constrain.getMessage()+"\n";
			}
			return ResponseEntity.badRequest().body(mensagem);
		
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	

}
