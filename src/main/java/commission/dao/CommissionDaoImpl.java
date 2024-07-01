package commission.dao;

import commission.entity.Commission;
import commission.mapper.CommissionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommissionDaoImpl implements  CrudDao<Commission> {

    private final JdbcTemplate jdbcTemplate;
    private final CommissionRowMapper rowMapper = new CommissionRowMapper();

    @Autowired
    public CommissionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Commission> findAll() {
        var query = "SELECT id, type, payment, sales_id FROM commission";
        return this.jdbcTemplate.query(query, this.rowMapper);
    }

    @Override
    public Optional<Commission> findById(long id) {
        var query = "SELECT id, type, payment, sales_id FROM commission WHERE id=?";
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(query, this.rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void insert(Commission commission) {
        var query =  "INSERT INTO commission(type) VALUES (?)";
        this.jdbcTemplate.update(query,commission.type());
    }

    @Override
    public void update(long id, Commission commission) {
        var query = "UPDATE commission SET type=? WHERE id=?";
        this.jdbcTemplate.update(query, commission.type(), id);
    }

    @Override
    public void delete(long id) {
        var query = "DELETE FROM commission WHERE id=?";
        this.jdbcTemplate.update(query, id);

    }
}
