package br.com.capelli.secretsanta.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "resultado")
public class Resultado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private String codigoPessoal;
	private String email;
	private String amigoSecreto;
	private Boolean visualizado;

	@ManyToOne
	@JoinColumn(name = "fk_id_sorteio")
	private Sorteio sorteio;

	public Resultado() {
		this.visualizado = Boolean.FALSE;
	}

	public Resultado(String nome, String codigoPessoal, String email,
			String amigoSecreto) {
		this.nome = nome;
		this.codigoPessoal = codigoPessoal;
		this.email = email;
		this.amigoSecreto = amigoSecreto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAmigoSecreto() {
		return amigoSecreto;
	}

	public void setAmigoSecreto(String amigoSecreto) {
		this.amigoSecreto = amigoSecreto;
	}

	public String getCodigoPessoal() {
		return codigoPessoal;
	}

	public void setCodigoPessoal(String codigoPessoal) {
		this.codigoPessoal = codigoPessoal;
	}

	public Boolean getVisualizado() {
		return visualizado;
	}

	public void setVisualizado(Boolean visualizado) {
		this.visualizado = visualizado;
	}

	public Sorteio getSorteio() {
		return sorteio;
	}

	public void setSorteio(Sorteio sorteio) {
		this.sorteio = sorteio;
	}

}
