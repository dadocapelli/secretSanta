package br.com.capelli.secretsanta.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.capelli.secretsanta.exception.DAOException;
import br.com.capelli.secretsanta.modelo.Amigo;
import br.com.capelli.secretsanta.modelo.Usuario;

@Stateless
public class AmigoRepository extends PersistenceRepository<Amigo, Long> {

    public AmigoRepository() {
        super(Amigo.class);
    }

    public List<Amigo> findAmigoByUsuario(Usuario usuario) throws DAOException {
        try {

            CriteriaBuilder criteriaBuilder = getEm().getCriteriaBuilder();
            CriteriaQuery<Amigo> criteriaQuery = criteriaBuilder
                    .createQuery(Amigo.class);
            Root<Amigo> entity = criteriaQuery.from(Amigo.class);

            criteriaQuery.select(entity).where(criteriaBuilder.equal(
                    entity.get("usuario").get("email"), usuario.getEmail()));

            return getEm().createQuery(criteriaQuery).getResultList();

        } catch (Exception e) {
            throw new DAOException("Erro ao buscar amigos. ", e);
        }

    }

}
