package br.com.capelli.secretsanta.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.capelli.secretsanta.dao.GenericDAO;
import br.com.capelli.secretsanta.dao.ResultadoDAO;
import br.com.capelli.secretsanta.dao.SorteioDAO;
import br.com.capelli.secretsanta.dao.UsuarioDAO;
import br.com.capelli.secretsanta.exception.ManagementException;
import br.com.capelli.secretsanta.manager.SorteioManager;
import br.com.capelli.secretsanta.modelo.Amigo;
import br.com.capelli.secretsanta.modelo.Resultado;
import br.com.capelli.secretsanta.modelo.Sorteio;
import br.com.capelli.secretsanta.modelo.Usuario;

@Stateless
public class SorteioManagerImpl extends AbstractManager<Sorteio, Long>
		implements SorteioManager {

	private static Logger logger = Logger.getLogger(SorteioManagerImpl.class);

	private @Inject
	SorteioDAO sorteioDAO;
	private @Inject
	UsuarioDAO usuarioDAO;
	private @Inject
	ResultadoDAO resultadoDAO;

	@Override
	protected GenericDAO<Sorteio, Long> getDAO() {
		return sorteioDAO;
	}

	@Override
	public Resultado obtemResultado(String codigoPessoal)
			throws ManagementException {
		try {

			return resultadoDAO.findByCodigoPessoal(codigoPessoal);

		} catch (Exception e) {
			throw new ManagementException(
					"Erro ao buscar resultado, tente depois.", e);
		}
	}

	@Override
	public void gerarSorteio(String login) throws ManagementException {

		logger.info("[BEGIN] gerarSorteio ");

		try {

			Usuario usuario = usuarioDAO.findByLogin(login);

			Sorteio sorteio = new Sorteio();
			sorteio.setUsuario(usuario);
			sorteio.setNome("Natal 2013");
			sorteio.setCodigo("SECRET2013");
			sorteio.setDescricao("Amigo Secreto 2013");
			sorteioDAO.save(sorteio);

			boolean sucesso = false;
			int tentativas = 0;
			while (!sucesso && tentativas < 5) {
				tentativas++;
				sucesso = SorteioHelper.sortear(sorteio, usuario);
				logger.info("#### RESULTADO tentativa " + tentativas + " - "
						+ sucesso);
			}

			if (sucesso) {
				sorteioDAO.update(sorteio);
				usuario.getSorteios().add(sorteio);
				usuarioDAO.update(usuario);
			} else {
				throw new ManagementException(
						"Esgotou o número de tentativas de sorteio.");
			}

		} catch (Exception e) {
			logger.error("Erro ao gerar sorteio.", e);
			throw new ManagementException("Erro ao gerar sorteio.", e);
		}

		logger.info("[END] gerarSorteio ");

	}

}
