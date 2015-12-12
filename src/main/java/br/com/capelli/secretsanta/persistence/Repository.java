package br.com.capelli.secretsanta.persistence;

import java.util.Optional;

public interface Repository<T, PK> {

    Optional<T> find(PK id);

    void remove(T entity);

    void persist(T entity);

    void update(T entity);
}
