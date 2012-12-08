package br.com.capelli.secretsanta.dao;

import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.modelo.Usuario;

public interface UsuarioDAO extends
		GenericDAO<Usuario, Long> {

	Usuario findByLogin(String login) throws DAOException;
	
}
