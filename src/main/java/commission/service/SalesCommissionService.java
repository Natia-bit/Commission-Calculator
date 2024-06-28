package commission.service;

import java.util.List;
import java.util.Optional;

public interface SalesCommissionService<T> {
    List<T> findAll();
    void insert(T entity);
    Optional<T> findById(long id);

    boolean delete(long id);
    boolean update(long id, T entity);

}

