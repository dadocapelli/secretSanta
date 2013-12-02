package br.com.capelli.secretsanta.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.capelli.secretsanta.exception.ManagementException;
import br.com.capelli.secretsanta.modelo.Amigo;
import br.com.capelli.secretsanta.modelo.Resultado;
import br.com.capelli.secretsanta.modelo.Sorteio;
import br.com.capelli.secretsanta.modelo.Usuario;

public class SorteioHelper {

	private static Logger logger = Logger.getLogger("SorteioHelper");

	public static boolean sortear(Sorteio sorteio, Usuario usuario)
			throws ManagementException {
		try {

			logger.info("[BEGIN] sortear");
			Resultado resultado;

			List<Amigo> amigosOriginal = new ArrayList<Amigo>();
			amigosOriginal.addAll(usuario.getAmigos());

			List<Amigo> amigosParaSortear = new ArrayList<Amigo>();
			amigosParaSortear.addAll(usuario.getAmigos());

			Collections.shuffle(amigosOriginal);

			int sizeOriginal = amigosOriginal.size();
			int tentativas = 0;

			Amigo amigo = amigosOriginal.get(0);
			Amigo amigoInicial = amigosOriginal.get(0);
			amigosParaSortear.remove(amigo);

			while (!amigosOriginal.isEmpty()
					&& tentativas <= (sizeOriginal * 3)) {
				tentativas++;
				logger.info("while tentativas: " + tentativas
						+ " amigosOriginal.size: " + amigosOriginal.size());
				// logger.info(amigosOriginal);

				logger.info("Sortear amigo de: " + amigo.getNome());

				Amigo amigoSorteado = null;
				if (amigosParaSortear.size() == 0) {
					amigoSorteado = amigoInicial;
				} else {
					amigoSorteado = amigoSorteado(amigosParaSortear);
				}
				logger.info("Nome sorteado: " + amigoSorteado.getNome()
						+ " total: " + amigosParaSortear.size());
				// logger.info(amigosParaSortear);

				if (validandoResultado(amigo, amigoSorteado)) {

					logger.info("SORTEADO");
					resultado = new Resultado();
					resultado.setEu(amigo);
					resultado.setMeuAmigoSecreto(amigoSorteado);
					resultado.setSorteio(sorteio);

					amigoSorteado.getAmigosProibidos().add(amigo);
					sorteio.getResultados().add(resultado);

					amigosParaSortear.remove(amigoSorteado);
					amigosOriginal.remove(amigo);
				}

				// O amigo Sorteado é o proximo a escolher
				amigo = amigoSorteado;

				logger.info("### ");

				// TODO update Sorteio para salvar resultado
				// resultadoDAO.save(resultado);
				// sorteio.getResultados().addAll(resultados);
			}

			return amigosOriginal.isEmpty();

		} catch (Exception e) {
			throw new ManagementException("Erro", e);
		} finally {
			logger.info("[END] sortear");
		}
	}

	private static Amigo amigoSorteado(List<Amigo> amigosParaSortear) {
		double r = Math.random();
		int s1 = ((int) (r * 1000) % amigosParaSortear.size());
		return amigosParaSortear.get(s1);
	}

	private static boolean validandoResultado(Amigo amigo, Amigo amigoSorteado) {
		return (!amigo.equals(amigoSorteado) && !amigo.getAmigosProibidos()
				.contains(amigoSorteado));
	}

}
