package br.com.capelli.secretsanta.dao.jpa;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.capelli.secretsanta.dao.AmigoDAO;
import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.modelo.Amigo;
import br.com.capelli.secretsanta.modelo.Usuario;

@Stateless
public class AmigoDAOJPA extends GenericDAOJPA<Amigo, Long> implements
		AmigoDAO, Serializable {

	private static final long serialVersionUID = 6617755632971828341L;

	public AmigoDAOJPA() throws DAOException {
		super(Amigo.class);
	}

	@Override
	public List<Amigo> findAmigoByUsuario(Usuario usuario) throws DAOException {
		try {

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Amigo> criteriaQuery = criteriaBuilder
					.createQuery(Amigo.class);
			Root<Amigo> entity = criteriaQuery.from(Amigo.class);

			criteriaQuery.select(entity).where(
					criteriaBuilder.equal(entity.get("usuario"), usuario));

			return em.createQuery(criteriaQuery).getResultList();

		} catch (Exception e) {
			throw new DAOException("Erro ao buscar amigos. ", e);
		}

	}

	// @Override
	// public Amigo findByLogin(String login) throws DAOException {
	//
	// try {
	//
	// CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
	// CriteriaQuery<Amigo> criteriaQuery = criteriaBuilder
	// .createQuery(Usuario.class);
	// Root<Usuario> entity = criteriaQuery.from(Usuario.class);
	//
	// List<Predicate> predicates = new ArrayList<Predicate>();
	//
	// predicates.add(criteriaBuilder.and(criteriaBuilder.equal(
	// entity.get("login"), login)));
	//
	// criteriaQuery.select(entity).where(
	// predicates.toArray(new Predicate[0]));
	//
	// return em.createQuery(criteriaQuery).getSingleResult();
	//
	// } catch (NoResultException e) {
	// return null;
	// }
	//
	// }

}
