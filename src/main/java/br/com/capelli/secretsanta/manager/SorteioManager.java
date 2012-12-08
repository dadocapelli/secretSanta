package br.com.capelli.secretsanta.manager;

import javax.ejb.Local;

import br.com.capelli.secretsanta.exception.ManagementException;
import br.com.capelli.secretsanta.modelo.Resultado;
import br.com.capelli.secretsanta.modelo.Sorteio;

@Local
public interface SorteioManager extends Manager<Sorteio, Long> {

	void gerarSorteio(String login) throws ManagementException;

	Resultado obtemResultado(String codigoPessoal) throws ManagementException;

}

