package br.com.capelli.secretsanta.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sorteio")
public class Sorteio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "fk_id_usuario")
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "fk_id_sorteio")
    private List<Participante> participantes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "fk_id_sorteio")
    private List<RestricaoSorteio> restricoes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sorteio", fetch = FetchType.EAGER)
    private List<Resultado> resultados;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Resultado> getResultados() {
        if (resultados == null) {
            resultados = new ArrayList<>();
        }
        return resultados;
    }

    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }

    public List<Participante> getParticipantes() {
        if (participantes == null) {
            participantes = new ArrayList<>();
        }
        return participantes;
    }

    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }

    public List<RestricaoSorteio> getRestricoes() {
        if (restricoes == null) {
            restricoes = new ArrayList<>();
        }
        return restricoes;
    }

    public void setRestricoes(List<RestricaoSorteio> restricoes) {
        this.restricoes = restricoes;
    }

}
