package commission.dao;

import commission.entity.Sale;
import commission.mapper.SalesRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SaleDaoImpl implements SaleDao{

    JdbcTemplate jdbcTemplate;

    @Autowired
    public SaleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Sale> findAll() {
        var query = "SELECT * FROM sales";
        return jdbcTemplate.query(query, new SalesRowMapper());
    }


    @Override
    public void insertSale(Sale sale) {
        var query = "INSERT INTO sales(price, percentage) VALUES (?,?)";
        jdbcTemplate.update(query, sale.price(), sale.percentage());
    }

    @Override
    public Sale findSaleById(long id) {
        var query = "SELECT * FROM sales WHERE id=?";
        System.out.println(jdbcTemplate.queryForObject(query, new SalesRowMapper(), id));
        return  jdbcTemplate.queryForObject(query, new SalesRowMapper(), id);
    }


    @Override
    public void deleteSale(long id) {
        var query = "DELETE FROM sales WHERE id=?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public void updateSale(long id, Sale sale) {
        var query = "UPDATE sales SET price=?, percentage=?  WHERE id=?";
        jdbcTemplate.update(query, sale.price(), sale.percentage(), id);
    }

    @Override
    public void updateSalePrice(long id, Sale sale) {
        var query = "UPDATE sales SET price=? WHERE id=?";
        jdbcTemplate.update(query, sale.price(), id);
    }

    @Override
    public void updateSalePercentage(long id, Sale sale) {
        var query = "UPDATE sales SET percentage=? WHERE id=?";
        jdbcTemplate.update(query, sale.percentage(), id);
    }
}
