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
	private String calca;
	private String camisa;

	@Override
	public String toString() {
		return "Idade: " + this.idade + " - Sapato: " + this.sapato
				+ " - Calça: " + this.calca + " - Camisa: " + this.camisa;
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

	public String getCalca() {
		return calca;
	}

	public void setCalca(String calca) {
		this.calca = calca;
	}

	public String getCamisa() {
		return camisa;
	}

	public void setCamisa(String camisa) {
		this.camisa = camisa;
	}

}
