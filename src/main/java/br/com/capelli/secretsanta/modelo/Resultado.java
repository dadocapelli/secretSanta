package br.com.capelli.secretsanta.modelo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "resultado")
public class Resultado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_id_eu")
	private Amigo eu;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_id_meu_amigo_secreto")
	private Amigo meuAmigoSecreto;
	private Boolean visualizado;

	@ManyToOne
	@JoinColumn(name = "fk_id_sorteio")
	private Sorteio sorteio;

	public Resultado() {
		this.visualizado = Boolean.FALSE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return this.eu.getNome() + " tirou " + this.meuAmigoSecreto.getNome();
	}

	public Amigo getEu() {
		return eu;
	}

	public void setEu(Amigo eu) {
		this.eu = eu;
	}

	public Amigo getMeuAmigoSecreto() {
		return meuAmigoSecreto;
	}

	public void setMeuAmigoSecreto(Amigo meuAmigoSecreto) {
		this.meuAmigoSecreto = meuAmigoSecreto;
	}

}
