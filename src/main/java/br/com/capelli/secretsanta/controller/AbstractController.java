package br.com.capelli.secretsanta.controller;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.capelli.secretsanta.dao.GenericDAO;
import br.com.capelli.secretsanta.exception.DAOException;

/**
 * Classe base para todos os controllers
 * 
 * @author cristiano
 * 
 * @param <T>
 *            Entidade que o Controller ira gerenciar
 * @param <M>
 *            Manager da entidade
 * @param <PK>
 *            Chave primária da entidade
 * @version 1.00
 * @since 1.00
 */
public abstract class AbstractController<T, DAO extends GenericDAO<T, PK>, PK extends Serializable> {

	protected Logger logger = Logger.getLogger(this.getClass());
	public static final String JSF_LIST = "list";
	public static final String JSF_EDIT = "edit";

	private String id;
	protected DAO dao;
	protected boolean novo = false;
	protected boolean editMode = false;
	protected String argTpEnd;

	public AbstractController(DAO dao) {
		this.dao = dao;
	}

	public AbstractController() {
	}

	private T entity;
	private List<T> lst;
	
	public String novo() {
		novo = true;
		editMode = true;
		return null;
	}

	public String editar() {
		novo = false;
		editMode = true;
		return JSF_EDIT;
	}

	public String salvarOuAtualizar() {
		try {
			if (isNovo()) {
				dao.save(getEntity());
			} else {
				dao.update(getEntity());
			}
			editMode = false;
			filtrar();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return JSF_LIST;
	}

	public String lista() {
		editMode = false;
		filtrar();
		return JSF_LIST;
	}

	public String delete() {
		try {
			dao.delete(getEntity());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		filtrar();
		return JSF_LIST;
	}

	public abstract void filtrar();

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		logger.info("calling set entity");
		this.entity = entity;
	}

	public List<T> getLst() {
		if (lst == null)
			filtrar();
		return lst;
	}

	public void setLst(List<T> lst) {
		this.lst = lst;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public boolean isNovo() {
		return novo;
	}

	public String getArgTpEnd() {
		return argTpEnd;
	}

	public void setArgTpEnd(String argTpEnd) {
		this.argTpEnd = argTpEnd;
	}
	
}
