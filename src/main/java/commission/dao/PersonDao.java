package commission.dao;

import commission.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao extends SalesCommissionDao<Person> {

    @Override
    List<Person> findAll();

    @Override
    Optional<Person> findById(long id);

    @Override
    void insert(Person entity);

    @Override
    void update(long id, Person person);

    @Override
    void delete(long id);



}
