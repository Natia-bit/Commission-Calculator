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
        String query = "SELECT * FROM sales";
        return jdbcTemplate.query(query, new SalesRowMapper());
    }


    @Override
    public void insertSale(Sale sale) {
        String query = "insert into sales(price) values (?);";
        jdbcTemplate.update(query, sale.price());
    }


}
