package com.mckeydonelly.servletdungeoncrawler.repositories;

public interface Repository<T,I> {
    void save(T entity);
    T findById(I id);
    Iterable<T> findAll();
    int count();
    void delete(T entity);
    boolean exists(I id);
}
