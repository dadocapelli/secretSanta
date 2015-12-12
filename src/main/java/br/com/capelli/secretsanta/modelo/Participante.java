package br.com.capelli.secretsanta.modelo;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "participante")
public class Participante implements Serializable {

    private static final long serialVersionUID = -5395975997526753970L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String codigo;
    private String email;

    @Embedded
    private Caracteristica meuAmigoDados;

    public static Participante from(Amigo amigo) {
        Participante participante = new Participante();
        participante.setNome(amigo.getNome());
        participante.setEmail(amigo.getEmail());
        participante.setCodigo(amigo.getCodigo());
        participante.setMeuAmigoDados(amigo.getCaracteristica());
        return participante;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Caracteristica getMeuAmigoDados() {
        return meuAmigoDados;
    }

    public void setMeuAmigoDados(Caracteristica meuAmigoDados) {
        this.meuAmigoDados = meuAmigoDados;
    }

}
