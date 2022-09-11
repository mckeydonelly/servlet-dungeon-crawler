package com.mckeydonelly.servletdungeoncrawler.repositories;

public interface Repository<T, ID> {
    void save(T entity);
    T findById(ID id);
    Iterable<T> findAll();
    int count();
    void delete(T entity);
    boolean exists(ID id);
}
