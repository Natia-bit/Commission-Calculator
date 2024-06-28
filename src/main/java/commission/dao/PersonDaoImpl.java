package commission.dao;

import commission.entity.Person;
import commission.mapper.PeopleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonDaoImpl implements SalesCommissionDao<Person>{

    private final JdbcTemplate jdbcTemplate;
    private final PeopleRowMapper rowMapper = new PeopleRowMapper();

    @Autowired
    public PersonDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> findAll() {
        var query = "SELECT * FROM people";
        return this.jdbcTemplate.query(query, this.rowMapper);
    }

    @Override
    public Optional<Person> findById(long id) {
        var query = "SELECT * FROM people WHERE id=?";
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(query, this.rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void insert(Person person) {
        var query = "INSERT INTO people(first_name, surname) VALUES (?,?)";
        this.jdbcTemplate.update(query, person.firstName(), person.surname());

    }

    @Override
    public void update(long id, Person person) {
        var query = "UPDATE people SET first_name=?, surname=? WHERE id=?";
        this.jdbcTemplate.update(query, person.firstName(), person.surname(), id);

    }

    @Override
    public void delete(long id) {
        var query = "DELETE FROM people WHERE id=?";
        this.jdbcTemplate.update(query, id);
    }


}
