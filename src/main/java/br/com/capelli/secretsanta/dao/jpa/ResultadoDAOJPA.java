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

import br.com.capelli.secretsanta.dao.ResultadoDAO;
import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.modelo.Resultado;

@Stateless
public class ResultadoDAOJPA extends GenericDAOJPA<Resultado, Long> implements
		ResultadoDAO, Serializable {

	private static final long serialVersionUID = 1296831483229086740L;

	public ResultadoDAOJPA() throws DAOException {
		super(Resultado.class);
	}

	@Override
	public Resultado findByCodigoPessoal(String codigoPessoal)
			throws DAOException {

		try {

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Resultado> criteriaQuery = criteriaBuilder
					.createQuery(Resultado.class);
			Root<Resultado> entity = criteriaQuery.from(Resultado.class);

			List<Predicate> predicates = new ArrayList<Predicate>();

			predicates.add(criteriaBuilder.and(criteriaBuilder.equal(
					entity.get("eu").get("codigo"), codigoPessoal)));

			criteriaQuery.select(entity).where(
					predicates.toArray(new Predicate[0]));

			return em.createQuery(criteriaQuery).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

}
