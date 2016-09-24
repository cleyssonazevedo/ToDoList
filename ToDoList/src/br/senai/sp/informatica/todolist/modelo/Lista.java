package br.senai.sp.informatica.todolist.modelo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Lista {
	@Id // id auto gerado
	/*
	 * gerar um id automatico e mostrar que e pra identificação
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/*
	 * gera padrão para a coluna como tamanho nao nulo e etc
	 */
	@Column(length = 100, nullable = false)
	private String titulo;
	
	/*
	 * identifica um pra muitos e cria relacionamento entre as tabelas mappedby
	 * indica uma chave estrangeira para classe lista evitando a criação de uma
	 * nova tabela cascade para o fazer uma inserção direta se entrar em um laço,
	 * o fetch faz com que ...
	 */
	@OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<ItemLista> itens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<ItemLista> getItens() {
		return itens;
	}

	public void setItens(List<ItemLista> itens) {
		this.itens = itens;
	}

	@JsonProperty(value = "feito")
	public boolean isRealizada() {
		for (ItemLista item : itens) {
			if (!item.isFeito()) {
				return false;
			}
		}
		return true;
	}

}
