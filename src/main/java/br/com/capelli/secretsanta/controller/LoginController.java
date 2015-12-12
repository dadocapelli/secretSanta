package br.com.capelli.secretsanta.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.modelo.Usuario;
import br.com.capelli.secretsanta.persistence.UsuarioRepository;
import br.com.capelli.secretsanta.util.LoggedIn;

@Named("loginController")
@SessionScoped
public class LoginController implements Serializable {

    private static final long serialVersionUID = 1334558144765121077L;
    private Logger logger = Logger.getLogger(LoginController.class);

    private Usuario usuarioLogado;

    @Inject
    private UsuarioRepository usuarioRepository;

    public void logout() {
        try {
            logger.info("[BEGIN] invalidadando sessão: LOGOUT");

            // Se configuração no Jboss não tiver o contextPath, redireciona
            // para raiz ("/")
            String redirect = "/";
            if (StringUtils.isNotBlank(FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestContextPath())) {
                redirect = FacesContext.getCurrentInstance()
                        .getExternalContext().getRequestContextPath();
            }

            FacesContext.getCurrentInstance().getExternalContext()
                    .invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(redirect);

            logger.info("[END] sessão finalizada: LOGOUT");
        } catch (Exception e) {
            logger.error("PROBLEMA para invalidar sessão: ");
            e.printStackTrace();
        }
    }

    @Produces
    @SessionScoped
    @LoggedIn
    public Usuario getUsuarioLogado() {
        if (usuarioLogado == null) {
            try {
                usuarioLogado = usuarioRepository
                        .findByLogin(FacesContext.getCurrentInstance()
                                .getExternalContext().getRemoteUser());

                ((HttpSession) FacesContext.getCurrentInstance()
                        .getExternalContext().getSession(true)).setAttribute(
                                "usuarioLogado", usuarioLogado.getEmail());

            } catch (DAOException e) {
                logger.error(
                        "Erro ao efetuar a busca do usuário " + e.getMessage(),
                        e);
            }
        }
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    @Produces
    @Named("contextPath")
    public String getContextPath() {
        return FacesContext.getCurrentInstance().getExternalContext()
                .getRequestContextPath();
    }

}
