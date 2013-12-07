package br.com.capelli.secretsanta.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "amigo")
public class Amigo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String codigo;
	@ManyToOne
	@JoinColumn(name = "fk_id_usuario")
	private Usuario usuario;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_id_caracteristica")
	private Caracteristica caracteristica;

	@Transient
	private List<Amigo> amigosProibidos;

	public Amigo() {
		this.caracteristica = new Caracteristica();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.nome == null) ? 0 : this.nome.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Amigo other = (Amigo) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return this.nome;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Caracteristica getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(Caracteristica caracteristica) {
		this.caracteristica = caracteristica;
	}

	public List<Amigo> getAmigosProibidos() {
		if (amigosProibidos == null) {
			amigosProibidos = new ArrayList<Amigo>();
		}
		return amigosProibidos;
	}

	public void setAmigosProibidos(List<Amigo> amigosProibidos) {
		this.amigosProibidos = amigosProibidos;
	}

}
