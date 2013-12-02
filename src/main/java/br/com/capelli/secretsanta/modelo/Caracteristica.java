package br.com.capelli.secretsanta.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "caracteristica")
public class Caracteristica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String idade;
	private String sapato;
	private String roupa;

	@Override
	public String toString() {
		return "Idade: " + this.idade + " - Sapato: " + this.sapato
				+ " Roupa: " + this.roupa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public String getSapato() {
		return sapato;
	}

	public void setSapato(String sapato) {
		this.sapato = sapato;
	}

	public String getRoupa() {
		return roupa;
	}

	public void setRoupa(String roupa) {
		this.roupa = roupa;
	}

}
