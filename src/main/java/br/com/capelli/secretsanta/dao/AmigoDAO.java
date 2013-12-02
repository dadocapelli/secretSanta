package br.com.capelli.secretsanta.dao;

import java.util.List;

import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.modelo.Amigo;
import br.com.capelli.secretsanta.modelo.Usuario;

public interface AmigoDAO extends
		GenericDAO<Amigo, Long> {

	List<Amigo> findAmigoByUsuario(Usuario usuario) throws DAOException;

}
