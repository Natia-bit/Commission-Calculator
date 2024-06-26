package commission.service;

import commission.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> findAll();
    void insert(Person person);
    Optional<Person> findById(long id);
    boolean delete(long id);
    boolean update(long id, Person person);

}
