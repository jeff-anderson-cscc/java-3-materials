package edu.cscc.hibernate;

import java.sql.SQLException;
import java.util.Optional;

public interface CrudRepository <T, ID> {
    Optional<T> findById(ID primaryKey);
    Iterable<T> findAll();
    T create(T entity);
    void update(T entity);
    void delete(ID primaryKey);
}