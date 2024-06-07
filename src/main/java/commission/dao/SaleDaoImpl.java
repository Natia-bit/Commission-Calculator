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
        var query = "INSERT INTO sales(price) VALUES (?)";
        jdbcTemplate.update(query, sale.price());
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
        jdbcTemplate.query(query, new SalesRowMapper(), id);
    }

}
