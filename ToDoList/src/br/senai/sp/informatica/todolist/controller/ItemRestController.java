package br.senai.sp.informatica.todolist.controller;

import java.net.URI;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.informatica.todolist.dao.ItemDAO;
import br.senai.sp.informatica.todolist.modelo.ItemLista;

@RestController
public class ItemRestController {
	@Autowired
	private ItemDAO itemDAO;

	@RequestMapping(value = "/item/{ID}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> MarcarFeito(@PathVariable("ID") long idItem,@RequestBody String strFeito) {
		try {
			// Criando um arquivo JSON, usando a string que eu recebi
			JSONObject jsn = new JSONObject(strFeito);
			itemDAO.MarcarFeito(idItem, jsn.getBoolean("feito"));
			HttpHeaders responseHeader = new HttpHeaders();
			URI location = new URI("/item/" + idItem);
			responseHeader.setLocation(location);
			
			return new ResponseEntity<Void>(responseHeader, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping (value="/lista/{ID}/item", method= RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ItemLista> AdicionarItem(@PathVariable("ID") long idLista, @RequestBody ItemLista item){
		try {
			itemDAO.Inserir(idLista, item);
			URI location = new URI("/item/" + item.getId());
			
			return ResponseEntity.created(location).body(item);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}