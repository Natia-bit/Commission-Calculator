package commission.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T>{

    List<T> findAll();
    Optional<T> findById(long id);
    void insert(T entity);
    void update(long id, T entity);
    void delete(long id);


}
