package br.com.capelli.secretsanta.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.modelo.Usuario;

@Stateless
public class UsuarioRepository extends PersistenceRepository<Usuario, String> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    public Usuario findByLogin(String email) throws DAOException {

        try {

            CriteriaBuilder criteriaBuilder = getEm().getCriteriaBuilder();
            CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder
                    .createQuery(Usuario.class);
            Root<Usuario> entity = criteriaQuery.from(Usuario.class);

            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(criteriaBuilder
                    .and(criteriaBuilder.equal(entity.get("email"), email)));

            criteriaQuery.select(entity)
                    .where(predicates.toArray(new Predicate[0]));

            return getEm().createQuery(criteriaQuery).getSingleResult();

        } catch (NoResultException e) {
            return null;
        }

    }

}
