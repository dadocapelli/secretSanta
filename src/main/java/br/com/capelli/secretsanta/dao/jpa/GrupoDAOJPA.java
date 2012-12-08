package br.com.capelli.secretsanta.dao.jpa;

import java.io.Serializable;

import javax.ejb.Stateless;

import br.com.capelli.secretsanta.dao.GrupoDAO;
import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.modelo.Grupo;

@Stateless
public class GrupoDAOJPA extends GenericDAOJPA<Grupo, String> implements
		GrupoDAO, Serializable {

	private static final long serialVersionUID = 5843801918946141090L;

	public GrupoDAOJPA() throws DAOException {
		super(Grupo.class);
	}

}
