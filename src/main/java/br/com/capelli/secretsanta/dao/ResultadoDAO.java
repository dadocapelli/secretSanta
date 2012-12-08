package br.com.capelli.secretsanta.dao;

import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.modelo.Resultado;


public interface ResultadoDAO extends GenericDAO<Resultado, Long> {

	Resultado findByCodigoPessoal(String codigoPessoal) throws DAOException;



}
