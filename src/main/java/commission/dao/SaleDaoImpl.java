package commission.dao;

import commission.entity.Sale;
import commission.mapper.SalesRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class SaleDaoImpl implements SaleDao{

    private final JdbcTemplate jdbcTemplate;
    private final SalesRowMapper rowMapper = new SalesRowMapper();

    @Autowired
    public SaleDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Sale> findAll() {
        var query = "SELECT * FROM sales";
        return this.jdbcTemplate.query(query, this.rowMapper);
    }


    @Override
    public void insertSale(Sale sale) {
        var query = "INSERT INTO sales(price, percentage, commission) VALUES (?,?,?)";
        this.jdbcTemplate.update(query, sale.price(), sale.percentage(), sale.commission());
    }

    @Override
    public Optional<Sale> findSaleById(long id) {
        var query = "SELECT * FROM sales WHERE id=?";
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(query, this.rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteSale(long id) {
        var query = "DELETE FROM sales WHERE id=?";
        this.jdbcTemplate.update(query, id);
    }

    @Override
    public void updateSale(long id, Sale sale) {
        var query = "UPDATE sales SET price=?, percentage=?, commission=?  WHERE id=?";
        this.jdbcTemplate.update(query, sale.price(), sale.percentage(), sale.commission(), id);
    }

    @Override
    public void updateSalePrice(long id, Sale sale) {
        var query = "UPDATE sales SET price=?, commission=? WHERE id=?";
        this.jdbcTemplate.update(query, sale.price(), sale.commission(), id);
    }

    @Override
    public void updateSalePercentage(long id, Sale sale) {
        var query = "UPDATE sales SET percentage=?, commission=? WHERE id=?";
        this.jdbcTemplate.update(query, sale.percentage(), sale.commission(), id);
    }
}
