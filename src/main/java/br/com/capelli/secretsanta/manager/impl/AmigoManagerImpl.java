package br.com.capelli.secretsanta.manager.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.capelli.secretsanta.dao.AmigoDAO;
import br.com.capelli.secretsanta.dao.GenericDAO;
import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.exception.ManagementException;
import br.com.capelli.secretsanta.manager.AmigoManager;
import br.com.capelli.secretsanta.modelo.Amigo;
import br.com.capelli.secretsanta.modelo.Usuario;

@Stateless
@Named("amigoManager")
@Default
public class AmigoManagerImpl extends AbstractManager<Amigo, Long> implements
		AmigoManager {

	private @Inject
	AmigoDAO amigoDAO;

	@Override
	protected GenericDAO<Amigo, Long> getDAO() {
		return amigoDAO;
	}

	@Override
	public List<Amigo> findAmigosByUsuario(Usuario usuario)
			throws ManagementException {
		try {

			return amigoDAO.findAmigoByUsuario(usuario);

		} catch (Exception e) {
			throw new ManagementException(
					"Erro ao buscar amigos, tente depois.", e);
		}
	}

	@Override
	public void saveOrUpdade(Amigo amigo) throws ManagementException {
		try {
			if (amigo.getId() == null) {
				amigoDAO.save(amigo);
			} else {
				amigoDAO.update(amigo);
			}
		} catch (DAOException e) {
			throw new ManagementException(e);
		}
	}

}
