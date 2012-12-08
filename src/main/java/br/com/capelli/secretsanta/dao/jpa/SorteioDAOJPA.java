package br.com.capelli.secretsanta.dao.jpa;

import java.io.Serializable;

import javax.ejb.Stateless;

import br.com.capelli.secretsanta.dao.SorteioDAO;
import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.modelo.Sorteio;

@Stateless
public class SorteioDAOJPA extends GenericDAOJPA<Sorteio, Long>
		implements SorteioDAO, Serializable {

	private static final long serialVersionUID = 1296831483229086740L;

	public SorteioDAOJPA() throws DAOException {
		super(Sorteio.class);
	}

}
