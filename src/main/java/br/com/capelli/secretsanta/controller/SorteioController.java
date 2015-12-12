package br.com.capelli.secretsanta.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.capelli.secretsanta.modelo.Resultado;
import br.com.capelli.secretsanta.modelo.Sorteio;
import br.com.capelli.secretsanta.modelo.Usuario;
import br.com.capelli.secretsanta.persistence.ResultadoRepository;
import br.com.capelli.secretsanta.service.SorteioService;
import br.com.capelli.secretsanta.util.LoggedIn;
import br.com.capelli.secretsanta.util.Util;

@Named("sorteioController")
@SessionScoped
public class SorteioController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(SorteioController.class);

    @Inject
    @LoggedIn
    private Usuario usuarioLogado;

    @Inject
    private SorteioService sorteioService;
    @Inject
    private ResultadoRepository resultadoRepository;

    private Resultado resultado = null;
    private String codigoPessoal = null;
    private boolean next = Boolean.FALSE;

    private Sorteio sorteioSelected;

    public void pesquisar() {
        try {
            String codigoLimpo = Util
                    .retiraCaracteres(codigoPessoal.replace(" ", ""))
                    .toLowerCase();

            resultado = sorteioService.obtemResultado(codigoLimpo);
            if (resultado == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Nenhum resultado encontrado com esse código: "
                                        + codigoPessoal
                                        + ". Confirme se digitou corretamente.",
                                ""));
            } else if (resultado.getVisualizado()) {

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Esse código pessoal já foi utilizado para visualizar o amigo secreto. Dúvidas procure o Bernardo Capelli.",
                                ""));

            } else {
                next = Boolean.TRUE;
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro inesperado, tente mais tarde.", ""));
        }

    }

    public void visualizado() {
        try {
            resultado.setVisualizado(Boolean.TRUE);
            resultadoRepository.update(resultado);
        } catch (Exception e) {
            logger.error("NAO ATUALIZOU O RESULTADO.", e);
        }
    }

    public void limparCampos() {
        codigoPessoal = null;
        next = Boolean.FALSE;
        resultado = null;
    }

    public void configurarSorteio() {

        try {
            logger.info(
                    "Amigos vazio?: " + usuarioLogado.getAmigos().isEmpty());

            sorteioService.configurarSorteio(usuarioLogado);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "SUCESSO.", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Erro ao gerar sorteio.", ""));
        }

    }

    public void sortear() {

        try {
            sorteioService.sorteando(usuarioLogado, sorteioSelected);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "SUCESSO.", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Erro ao gerar sorteio.", ""));
        }
    }

    public void enviarEmail() {

        try {
            sorteioService.enviarEmail(usuarioLogado, sorteioSelected);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "SUCESSO.", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Erro ao enviar email.", ""));
        }
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public String getCodigoPessoal() {
        return codigoPessoal;
    }

    public void setCodigoPessoal(String codigoPessoal) {
        this.codigoPessoal = codigoPessoal;
    }

    public Sorteio getSorteioSelected() {
        return sorteioSelected;
    }

    public void setSorteioSelected(Sorteio sorteioSelected) {
        this.sorteioSelected = sorteioSelected;
    }

}
