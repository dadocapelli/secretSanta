package br.com.capelli.secretsanta.dao.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.capelli.secretsanta.dao.GenericDAO;
import br.com.capelli.secretsanta.exception.DAOException;

/**
 * Implementação GenericDAOPattern para o JPA
 * 
 * @author cristiano
 * 
 * @param <T>
 *            a Classe
 * @param <PK>
 *            a Classe que representa o id
 */
@TransactionManagement(TransactionManagementType.BEAN)
abstract class GenericDAOJPA<T, PK extends Serializable> implements
		GenericDAO<T, PK> {

	@PersistenceContext(name = "amigosecretoPU")
	protected EntityManager em;

	protected void beginTransaction() {
		try {
			// TODO: Colocar transação quando possível, JTA EntityManager não
			// permite pegar transação
			// em.getTransaction().begin();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Cannot begin transaction.", e);
		}
	}

	protected void rollbackTransaction() {
		try {
			// TODO: Colocar transação quando possível, JTA EntityManager não
			// permite pegar transação
			// em.getTransaction().rollback();
		} catch (Exception t) {
			logger.log(Level.SEVERE, "Cannot rollback!", t);
		}
	}

	protected void commitTransaction() {
		try {
			// TODO: Colocar transação quando possível, JTA EntityManager não
			// permite pegar transação
			// em.getTransaction().commit();
		} catch (Exception t) {
			logger.log(Level.SEVERE, "Cannot commit!", t);
		}
	}

	private Class<T> classType;
	private static Logger logger = Logger.getLogger("GenericDAOJPA");

	public GenericDAOJPA(Class<T> classType) throws DAOException {
		if(classType == null) throw new DAOException("ClassType cannot be null");
		this.classType = classType;
	}

	@Override
	public void save(T obj) throws DAOException {
		try {
			beginTransaction();
			em.persist(obj);
			em.flush();
			commitTransaction();
		} catch (PersistenceException e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Could not save entity [")
							.append(obj != null ? obj.getClass().getName()
									: "NULL").append("] Rolling Back...")
							.toString(), e);
			throw new DAOException(e);
		} catch (NullPointerException e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Could not save entity [")
							.append(obj != null ? obj.getClass().getName()
									: "NULL").append("] Rolling back... ")
							.toString(), e);
			throw new DAOException(e);
		} catch (RuntimeException e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Could not save entity [")
							.append(obj != null ? obj.getClass().getName()
									: "NULL").append("] Rolling Back...")
							.toString(), e);
			throw new DAOException(e);
		} catch (Exception e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Could not save entity [")
							.append(obj != null ? obj.getClass().getName()
									: "NULL").append("] Rolling Back...")
							.toString(), e);
			throw new DAOException(e);
		}
	}

	@Override
	public T findById(PK id) throws DAOException, EntityNotFoundException {

		try {
			logger.info("findById [" +id +"] [BEGIN]" );
			logger.info("Finding entity " + classType.getSimpleName() +" with id [" + id +"]");
			T obj = em.find(classType, id);

			if (obj == null) {
				throw new EntityNotFoundException(new StringBuilder("Entity ")
						.append(classType != null ? classType.getName()
								: "NULL").append(" with id ").append(id)
						.append(" not found").toString());
			}

			return obj;

		} catch (NullPointerException e) {
			throw new DAOException(e);
		} catch (javax.persistence.EntityNotFoundException e) {
			return null;
		} finally {
			logger.info("FindById ["+id+"] done [END]");
		}
	}

	@Override
	public void update(T obj) throws DAOException {

		try {
			logger.log(Level.INFO, "Begin calling update method");
			beginTransaction();
			obj = em.merge(obj);
			em.flush();
			commitTransaction();
			logger.log(Level.INFO, "Update commited");
		} catch (PersistenceException e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Could not update entity [").append(
							obj != null ? obj.getClass().getName() : "NULL")
							.toString(), e);
			throw new DAOException(e);
		} catch (RuntimeException e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Something wrong occured [")
							.append(obj != null ? obj.getClass().getName()
									: "NULL").append("]").toString(), e);
			throw new DAOException(e);
		} catch (Exception e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Something wrong occured [")
							.append(obj != null ? obj.getClass().getName()
									: "NULL").append("]").toString(), e);
			throw new DAOException(e);
		} finally {
			logger.log(Level.FINE, "Update done");
		}
	}

	@Override
	public void delete(T obj) throws DAOException {
		try {
			logger.log(Level.INFO, "Deleting object...");
			beginTransaction();
			obj = em.merge(obj);
			em.remove(obj);
			em.flush();
			commitTransaction();
		} catch (PersistenceException e) {
			rollbackTransaction();
			throw new DAOException(e);
		} catch (NullPointerException e) {
			rollbackTransaction();
			throw new DAOException(e);
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw new DAOException(e);
		} catch (Exception e) {
			rollbackTransaction();
			throw new DAOException(e);
		}
	}

	@Override
	public List<T> findAll() throws DAOException {
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder
					.createQuery(classType);
			Root<T> entity = criteriaQuery.from(classType);
			criteriaQuery.select(entity);
			return em.createQuery(criteriaQuery).getResultList();
		} catch (PersistenceException e) {
			logger.log(Level.SEVERE, new StringBuilder(
					"Could not get entities. ").toString(), e);
			throw new DAOException(e);

		} catch (NullPointerException e) {
			logger.log(Level.SEVERE, new StringBuilder(
					"Could not get entities. ").toString(), e);
			throw new DAOException(e);
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, new StringBuilder(
					"Could not get entities. ").toString(), e);
			throw new DAOException(e);
		} catch (Exception e) {
			logger.log(Level.SEVERE, new StringBuilder(
					"Could not get entities. ").toString(), e);
			throw new DAOException(e);
		}
	}

}
