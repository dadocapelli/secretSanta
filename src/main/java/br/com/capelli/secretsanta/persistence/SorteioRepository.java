package br.com.capelli.secretsanta.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.modelo.Sorteio;

@Stateless
public class SorteioRepository extends PersistenceRepository<Sorteio, Long> {

    public SorteioRepository() {
        super(Sorteio.class);
    }

    public List<Sorteio> findByLogin(String email) throws DAOException {

        try {
            CriteriaBuilder criteriaBuilder = getEm().getCriteriaBuilder();
            CriteriaQuery<Sorteio> criteriaQuery = criteriaBuilder
                    .createQuery(Sorteio.class);
            Root<Sorteio> entity = criteriaQuery.from(Sorteio.class);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(criteriaBuilder.and(criteriaBuilder
                    .equal(entity.get("usuario").get("email"), email)));

            criteriaQuery.select(entity)
                    .where(predicates.toArray(new Predicate[0]));

            return getEm().createQuery(criteriaQuery).getResultList();

        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

    }

}
