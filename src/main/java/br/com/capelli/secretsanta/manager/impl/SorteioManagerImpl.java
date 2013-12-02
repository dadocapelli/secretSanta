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
				// sucesso = sortear(sorteio, usuario);
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

	// public boolean sortear(Sorteio sorteio, Usuario usuario)
	// throws ManagementException {
	// try {
	//
	// logger.info("[BEGIN] sortear");
	// Resultado resultado;
	//
	// List<Amigo> amigosOriginal = new ArrayList<Amigo>();
	// amigosOriginal.addAll(usuario.getAmigos());
	//
	// List<Amigo> amigosParaSortear = new ArrayList<Amigo>();
	// amigosParaSortear.addAll(usuario.getAmigos());
	//
	// Collections.shuffle(amigosOriginal);
	//
	// // List<Resultado> resultados = new ArrayList<Resultado>();
	//
	// int sizeOriginal = amigosOriginal.size();
	// int tentativas = 0;
	//
	// Amigo amigo = amigosOriginal.get(0);
	// while (!amigosOriginal.isEmpty()
	// && tentativas <= (sizeOriginal * 3)) {
	// tentativas++;
	// logger.info("while tentativas: " + tentativas
	// + " amigosOriginal.size: " + amigosOriginal.size());
	//
	// for (int i = 0; i <= amigosOriginal.size(); i++) {
	// logger.info("while tentativas: " + tentativas
	// + " amigosOriginal.size: " + amigosOriginal.size()
	// + " i= " + i);
	//
	// logger.info("Sortear amigo de: " + amigo.getNome());
	//
	// Amigo amigoSorteado = amigoSorteado(amigosParaSortear);
	// logger.info("Nome sorteado: " + amigoSorteado.getNome());
	//
	// if (validandoResultado(amigo, amigoSorteado)) {
	//
	// logger.info("SORTEADO");
	// resultado = new Resultado();
	// resultado.setNome(amigo.getNome());
	// resultado.setCodigoPessoal(amigo.getCodigo());
	// resultado.setAmigoSecreto(amigoSorteado.getNome());
	// resultado.setSorteio(sorteio);
	//
	// // resultados.add(resultado);
	//
	// amigoSorteado.getAmigosProibidos().add(amigo);
	// sorteio.getResultados().add(resultado);
	//
	// amigosParaSortear.remove(amigoSorteado);
	// amigosOriginal.remove(i);
	// }
	//
	// // O amigo Sorteado é o proximo a escolher
	// amigo = amigoSorteado;
	//
	// // sair do for e recomeçar
	// if (amigosOriginal.size() == i + 1) {
	// i++;
	// }
	//
	// logger.info("### ");
	// }
	//
	// // TODO update Sorteio para salvar resultado
	// // resultadoDAO.save(resultado);
	// // sorteio.getResultados().addAll(resultados);
	// }
	//
	// return amigosOriginal.isEmpty();
	//
	// } catch (Exception e) {
	// throw new ManagementException("Erro", e);
	// } finally {
	// logger.info("[END] sortear");
	// }
	// }
	//
	// private Amigo amigoSorteado(List<Amigo> amigosParaSortear) {
	// double r = Math.random();
	// int s1 = ((int) (r * 1000) % amigosParaSortear.size());
	// return amigosParaSortear.get(s1);
	// }
	//
	// private boolean validandoResultado(Amigo amigo, Amigo amigoSorteado) {
	// return (!amigo.equals(amigoSorteado) && !amigo.getAmigosProibidos()
	// .contains(amigoSorteado));
	// }

}
