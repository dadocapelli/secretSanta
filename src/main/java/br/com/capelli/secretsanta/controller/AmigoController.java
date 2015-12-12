package br.com.capelli.secretsanta.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.capelli.secretsanta.exception.ServiceException;
import br.com.capelli.secretsanta.modelo.Amigo;
import br.com.capelli.secretsanta.modelo.Usuario;
import br.com.capelli.secretsanta.service.AmigoService;
import br.com.capelli.secretsanta.util.LoggedIn;

@Named("amigoController")
@SessionScoped
public class AmigoController implements Serializable {

    private static final long serialVersionUID = 6595523276504148955L;
    private final Logger logger = Logger.getLogger("AmigoControllerLogger");

    @Inject
    @LoggedIn
    private Usuario usuarioLogado;
    @Inject
    private AmigoService amigoService;

    private Boolean editMode = Boolean.FALSE;

    private List<Amigo> amigos;
    private Amigo amigoSelected;

    public void doFilter() {
        try {
            amigos = amigoService.findAmigosByUsuario(usuarioLogado);
        } catch (Exception e) {
            logger.error(e);
            amigos = new ArrayList<Amigo>();
        }
    }

    public String edit() {
        setEditMode(Boolean.TRUE);
        return "/pages/cadastro/editAmigo?faces-redirect=true";
    }

    public String list() {
        doFilter();
        return "/pages/cadastro/listAmigo?faces-redirect=true";
    }

    public String novo() {
        amigoSelected = new Amigo();
        amigoSelected.setUsuario(usuarioLogado);
        setEditMode(Boolean.FALSE);
        return "/pages/cadastro/editAmigo?faces-redirect=true";

    }

    public String save() {
        try {
            amigoService.saveOrUpdade(amigoSelected);
            return list();

        } catch (ServiceException e) {
            logger.error("Erro ao salvar a Amigo: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro ao salvar a Amigo.", ""));
        }
        return null;
    }

    public List<Amigo> getAmigos() {
        doFilter();
        return amigos;
    }

    public void setAmigos(List<Amigo> amigos) {
        this.amigos = amigos;
    }

    public Amigo getAmigoSelected() {
        return amigoSelected;
    }

    public void setAmigoSelected(Amigo amigoSelected) {
        this.amigoSelected = amigoSelected;
    }

    public Boolean getEditMode() {
        return editMode;
    }

    public void setEditMode(Boolean editMode) {
        this.editMode = editMode;
    }

}
