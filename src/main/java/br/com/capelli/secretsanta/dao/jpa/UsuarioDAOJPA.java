package br.com.capelli.secretsanta.dao.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.capelli.secretsanta.dao.UsuarioDAO;
import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.modelo.Usuario;

@Stateless
public class UsuarioDAOJPA extends GenericDAOJPA<Usuario, Long> implements
		UsuarioDAO, Serializable {

	private static final long serialVersionUID = 6617755632971828341L;

	public UsuarioDAOJPA() throws DAOException {
		super(Usuario.class);
	}

	@Override
	public Usuario findByLogin(String login) throws DAOException {

		try {

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder
					.createQuery(Usuario.class);
			Root<Usuario> entity = criteriaQuery.from(Usuario.class);

			List<Predicate> predicates = new ArrayList<Predicate>();

			predicates.add(criteriaBuilder.and(criteriaBuilder.equal(
					entity.get("login"), login)));

			criteriaQuery.select(entity).where(
					predicates.toArray(new Predicate[0]));

			return em.createQuery(criteriaQuery).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

}
