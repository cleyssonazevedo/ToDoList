package br.senai.sp.informatica.todolist.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.informatica.todolist.dao.ListaDAO;
import br.senai.sp.informatica.todolist.modelo.ItemLista;
import br.senai.sp.informatica.todolist.modelo.Lista;

@RestController
public class ListaRestController {
	/*
	 * 
	 */
	@Autowired
	private ListaDAO listaDao;

	/*
	 * No RequestMapping, o primeiro é o nome que vai ser usado pelo navegador,
	 * o segundo o método usado, se vai receber ou enviar e o terceiro para que
	 * ele trate o que ele vai receber
	 */
	@RequestMapping(value = "/lista", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lista> inserir(@RequestBody String strLista) {
		try {
			JSONObject job = new JSONObject(strLista);
			Lista lista = new Lista();
			lista.setTitulo(job.getString("titulo"));
			List<ItemLista> itens = new ArrayList<>();
			JSONArray arrayItens = job.getJSONArray("itens");
			for (int i = 0; i < arrayItens.length(); i++) {
				ItemLista item = new ItemLista();
				item.setDescricao(arrayItens.getString(i));
				item.setLista(lista);
				itens.add(item);
			}
			lista.setItens(itens);
			listaDao.inserir(lista);
			/*
			 * Local onde irá os dados serão enviados, sendo assim, se for
			 * acessado no navegador /todo/ID, ele irá aparecer o item inserido
			 */
			URI location = new URI("/lista/" + lista.getId());
			return ResponseEntity.created(location).body(lista);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * Com esse ResponseEntity, caso esteja nulo ele responde com o erro 404,
	 * sem ele, o retorno seria nulo
	 */
	@RequestMapping(value = "/lista", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Lista> ListarTudo() {
		return listaDao.Listar();
	}
	
	// O valor ID, está em chaves pois ele é um valor variavel
	@RequestMapping (value="/lista/{ID}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> ExcluirLista(@PathVariable("ID") long idLista){
		listaDao.ExcluirLista(idLista);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/item/{ID}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> ExcluirItem(@PathVariable("ID") long idItem){
		listaDao.ExcluirItem(idItem);
		return ResponseEntity.noContent().build();
	}
}