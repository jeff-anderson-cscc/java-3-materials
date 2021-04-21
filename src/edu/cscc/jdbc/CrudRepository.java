package edu.cscc.jdbc;

import java.sql.SQLException;
import java.util.Optional;

public interface CrudRepository <T, ID> {
    T create(T entity) throws SQLException;
    Iterable<T> findAll() throws SQLException;
    Optional<T> findById(ID primaryKey) throws SQLException;
    int update(T entity) throws SQLException;
    int delete(ID primaryKey) throws SQLException;
}
