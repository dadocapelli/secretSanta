package br.com.capelli.secretsanta.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

	private @Inject SorteioDAO sorteioDAO;
	private @Inject UsuarioDAO usuarioDAO;
	private @Inject ResultadoDAO resultadoDAO;
	
	private Sorteio sorteio;

	@Override
	protected GenericDAO<Sorteio, Long> getDAO() {
		return sorteioDAO;
	}

	@Override
	public Resultado obtemResultado(String codigoPessoal) throws ManagementException {
		try {
			
			return resultadoDAO.findByCodigoPessoal(codigoPessoal);
			
		} catch (Exception e) {
			throw new ManagementException("Erro ao buscar resultado, tente depois.", e);
		}
	}

	@Override
	public void gerarSorteio(String login) throws ManagementException {
		
		logger.info("[BEGIN] gerarSorteio ");

		try {
			
			Usuario usuario = usuarioDAO.findByLogin(login);
			
			
			sorteio = new Sorteio();
			sorteio.setUsuario(usuario);
			sorteio.setNome("Natal 2012");
			sorteio.setCodigo("SECRET2012");
			sorteio.setDescricao("Amigo Secreto 2012");
			sorteioDAO.save(sorteio);

			boolean sucesso = false;
			int tentativas = 0;
			while (!sucesso && tentativas < 5) {
				tentativas++;
				sucesso = sortear(usuario);
				logger.info("#### RESULTADO tentativa " + tentativas + " - "
						+ sucesso);

			}
			
			if(sucesso){
				
				sorteioDAO.update(sorteio);
				
				usuario.getSorteios().add(sorteio);
				usuarioDAO.update(usuario);
			} else {
				throw new ManagementException("Esgotou o número de tentativas de sorteio.");
			}

		} catch (Exception e) {
			logger.error("Erro ao gerar sorteio.", e);
			throw new ManagementException("Erro ao gerar sorteio.", e);
		}

		logger.info("[END] gerarSorteio ");

	}

	private boolean sortear(Usuario usuario) throws ManagementException {
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

			while (!amigosOriginal.isEmpty()
					&& tentativas <= (sizeOriginal * 3)) {
				tentativas++;
				logger.info("while tentativas: " + tentativas
						+ " amigosOriginal.size: " + amigosOriginal.size());

				for (int i = 0; i <= amigosOriginal.size(); i++) {
					logger.info("while tentativas: " + tentativas
							+ " amigosOriginal.size: " + amigosOriginal.size()
							+ " i= " + i);

					Amigo amigo = amigosOriginal.get(i);
					logger.info("tentativa " + tentativas);

					logger.info("Sortear amigo de: " + amigo.getNome());

					double r = Math.random();
					int s1 = ((int) (r * 1000) % amigosParaSortear.size());

					Amigo amigoSorteado = amigosParaSortear.get(s1);
					logger.info("Nome sorteado: " + amigoSorteado.getNome());

//					TODO ver se nao tiraram um ao outro
					if (!amigo.equals(amigoSorteado)
							&& !amigo.getAmigosProibidos().contains(
									amigoSorteado)
							&& !amigoSorteado.getAmigosProibidos().contains(
									amigo)) {

						logger.info("SORTEADO");
						resultado = new Resultado();
						resultado.setNome(amigo.getNome());
						resultado.setCodigoPessoal(amigo.getCodigo());
						resultado.setAmigoSecreto(amigoSorteado.getNome());
						resultado.setSorteio(sorteio);
						
						resultadoDAO.save(resultado);
						
						sorteio.getResultados().add(resultado);
						
						
						for(Amigo aux : amigosParaSortear){
							if(aux.equals(amigoSorteado)){
								aux.getAmigosProibidos().add(amigo);
							}
						}
						
						
						amigosParaSortear.remove(amigoSorteado);
						amigosOriginal.remove(i);
					}

					// sair do for e recomeçar
					if (amigosOriginal.size() == i + 1) {
						i++;
					}

					logger.info("### ");

				}
			}

			return amigosOriginal.isEmpty();

		} catch (Exception e) {
			throw new ManagementException("Erro", e);
		} finally {
			logger.info("[END] sortear");
		}

	}

}
