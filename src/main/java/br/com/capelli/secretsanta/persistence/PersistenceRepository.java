package br.com.capelli.secretsanta.persistence;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class PersistenceRepository<T, PK>
        implements Repository<T, PK> {

    @PersistenceContext
    private EntityManager em;
    private Class<T> clazz;

    protected PersistenceRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public PersistenceRepository() {
    }

    protected EntityManager getEm() {
        return this.em;
    }

    @Override
    public Optional<T> find(PK id) {
        return Optional.ofNullable(em.find(clazz, id));
    }

    @Override
    public void remove(T entity) {
        entity = em.merge(entity);
        em.remove(entity);
        em.flush();
    }

    @Override
    public void persist(T entity) {
        em.persist(entity);
        em.flush();
    }

    @Override
    public void update(T entity) {
        em.merge(entity);
        em.flush();
    }

}
