package br.com.capelli.secretsanta.manager.impl;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.capelli.secretsanta.dao.GenericDAO;
import br.com.capelli.secretsanta.dao.UsuarioDAO;
import br.com.capelli.secretsanta.manager.UsuarioManager;
import br.com.capelli.secretsanta.modelo.Usuario;

@Stateless
@Named("usuarioManager")
@Default
public class UsuarioManagerImpl extends AbstractManager<Usuario, Long>
		implements UsuarioManager {

	private @Inject
	UsuarioDAO usuarioDAO;

	@Override
	protected GenericDAO<Usuario, Long> getDAO() {
		return usuarioDAO;
	}

}
