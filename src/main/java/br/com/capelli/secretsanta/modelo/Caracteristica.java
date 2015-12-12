package br.com.capelli.secretsanta.modelo;

import javax.persistence.Embeddable;

@Embeddable
public class Caracteristica {

    private String idade;
    private String sapato;
    private String calca;
    private String camisa;

    @Override
    public String toString() {
        return "Idade: " + this.idade + " - Sapato: " + this.sapato
                + " - Calça: " + this.calca + " - Camisa: " + this.camisa;
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
