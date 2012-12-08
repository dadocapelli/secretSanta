package br.com.capelli.secretsanta.manager.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import br.com.capelli.secretsanta.dao.GenericDAO;
import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.exception.ManagementException;
import br.com.capelli.secretsanta.exception.ManagerTransactionException;
import br.com.capelli.secretsanta.manager.Manager;

public abstract class AbstractManager<T, PK extends Serializable> implements
		Manager<T, PK> {
	
	@Resource
	UserTransaction transaction;

	@Override
	public void save(T obj) throws ManagementException {
		try {
			getDAO().save(obj);
		} catch (DAOException e) {
			throw new ManagementException(e);
		} catch(NullPointerException e) {
			throw new ManagementException(e);
		}
	}

	@Override
	public T findById(PK id) throws ManagementException {

		try {
			return getDAO().findById(id);
		} catch (DAOException e) {
			throw new ManagementException(e);
		}

	}

	@Override
	public void update(T obj) throws ManagementException {
		try {
			getDAO().update(obj);
		} catch (DAOException e) {
			throw new ManagementException(e);
		}
	}

	@Override
	public void delete(T obj) throws ManagementException {
		try {
			getDAO().delete(obj);
		} catch (DAOException e) {
			throw new ManagementException(e);
		}
	}

	@Override
	public List<T> findAll() throws ManagementException {
		try {
			return getDAO().findAll();
		} catch (DAOException e) {
			throw new ManagementException(e);
		}
	}
	
	protected void beginTransaction() throws ManagerTransactionException {
		try {
			transaction.begin();
		} catch (NotSupportedException e) {
			throw new ManagerTransactionException(e);
		} catch (SystemException e) {
			throw new ManagerTransactionException(e);
		}
	}
	
	protected void commitTransaction() throws ManagerTransactionException {
		try {
			transaction.commit();
		} catch (SecurityException e) {
			throw new ManagerTransactionException(e);
		} catch (IllegalStateException e) {
			throw new ManagerTransactionException(e);
		} catch (RollbackException e) {
			throw new ManagerTransactionException(e);
		} catch (HeuristicMixedException e) {
			throw new ManagerTransactionException(e);
		} catch (HeuristicRollbackException e) {
			throw new ManagerTransactionException(e);
		} catch (SystemException e) {
			throw new ManagerTransactionException(e);
		}
	}
	
	protected void rollbackTransaction() throws ManagerTransactionException {
		try {
			transaction.rollback();
		} catch (IllegalStateException e) {
			throw new ManagerTransactionException(e);
		} catch (SecurityException e) {
			throw new ManagerTransactionException(e);
		} catch (SystemException e) {
			throw new ManagerTransactionException(e);
		}
	}

	protected abstract GenericDAO<T, PK> getDAO();

}
