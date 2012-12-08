package br.com.capelli.secretsanta.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import br.com.capelli.secretsanta.exception.DAOException;
/**
 * Interface de abstracao do Banco de Dados
 * GenericDAO pattern
 * 
 * http://www.ibm.com/developerworks/java/library/j-genericdao/index.html
 * 
 * @author Cristiano Andrade
 *
 * @param <T>
 * @param <PK>
 */
public interface GenericDAO <T, PK extends Serializable> {
	/**
	 * Persiste um novo Objeto no banco
	 * @param obj
	 * 				O objeto <code>T</code>
	 * @return
	 * 			O id do objeto
	 */
	void save(T obj) throws DAOException;
	/**
	 * Busca um objeto pelo id
	 * @param id
	 * 			O id do objeto
	 * @return
	 * 			O objeto
	 * @throws EntityNotFoundException, DAOException 
	 */
	T findById(PK id) throws DAOException;
	/**
	 * Atualiza um objeto no banco
	 * @param obj
	 * 				O objeto
	 */
	void update(T obj) throws DAOException;
	/**
	 * Remove o objeto do banco
	 * @param obj
	 * 
	 */
	void delete(T obj) throws DAOException;
	/**
	 * Recupera todos os objetos do banco
	 * @return
	 * 			Um <code>java.util.List</code> com os objetos
	 */
	List<T> findAll() throws DAOException;
	
	
}
