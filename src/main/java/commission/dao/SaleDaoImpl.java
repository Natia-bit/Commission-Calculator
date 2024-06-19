package commission.dao;

import commission.entity.Sale;
import commission.mapper.SalesRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        return jdbcTemplate.query(query, rowMapper);
    }


    @Override
    public void insertSale(Sale sale, double commission) {
        var query = "INSERT INTO sales(price, percentage, commission) VALUES (?,?,?)";
        jdbcTemplate.update(query, sale.price(), sale.percentage(), commission);
    }

    @Override
    public Optional<Sale> findSaleById(long id) {
        var query = "SELECT * FROM sales WHERE id=?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, rowMapper, id));
    }

    @Override
    public Sale findById(long id) {
        var query = "SELECT * FROM sales WHERE id=?";
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    @Override
    public void deleteSale(long id) {
        var query = "DELETE FROM sales WHERE id=?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public void updateSale(long id, Sale sale, double commission) {
        var query = "UPDATE sales SET price=?, percentage=?, commission=?  WHERE id=?";
        jdbcTemplate.update(query, sale.price(), sale.percentage(), commission, id);
    }

    @Override
    public void updateSalePrice(long id, Sale sale, double commission) {
        var query = "UPDATE sales SET price=?, commission=? WHERE id=?";
        jdbcTemplate.update(query, sale.price(), commission, id);
    }

    @Override
    public void updateSalePercentage(long id, Sale sale, double commission) {
        var query = "UPDATE sales SET percentage=?, commission=? WHERE id=?";
        jdbcTemplate.update(query, sale.percentage(), commission, id);
    }

}
