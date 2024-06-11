package commission.dao;

import commission.entity.Sale;
import commission.mapper.SalesRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
        var query = "INSERT INTO sales(price, percentage, commission) VALUES (?,?,?)";
        jdbcTemplate.update(query, sale.price(), sale.percentage(), commissionPerSale(sale));
    }

    @Override
    public Optional<Sale> findSaleById(long id) {
        var query = "SELECT * FROM sales WHERE id=?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new SalesRowMapper(), id));
    }

    @Override
    public Sale findById(long id) {
        var query = "SELECT * FROM sales WHERE id=?";
        return jdbcTemplate.queryForObject(query, new SalesRowMapper(), id);
    }

    @Override
    public void deleteSale(long id) {
        var query = "DELETE FROM sales WHERE id=?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public void updateSale(long id, Sale sale) {
        var query = "UPDATE sales SET price=?, percentage=?, commission=?  WHERE id=?";
        jdbcTemplate.update(query, sale.price(), sale.percentage(), commissionPerSale(sale), id);
    }

    @Override
    public void updateSalePrice(long id, Sale sale) {
        var tempPercentage = findById(id).percentage();
        var query = "UPDATE sales SET price=?, commission=? WHERE id=?";
        jdbcTemplate.update(query, sale.price(), commissionPerSale(sale.price(), tempPercentage), id);
    }

    @Override
    public void updateSalePercentage(long id, Sale sale) {
        var tempPrice = findById(id).price();
        var query = "UPDATE sales SET percentage=?, commission=? WHERE id=?";
        jdbcTemplate.update(query, sale.percentage(), commissionPerSale(sale.percentage(), tempPrice), id);
    }

    public double commissionPerSale(Sale sale){
       return sale.price() * sale.percentage() / 100 ;
    }

    public double commissionPerSale(double valueA, double valueB){
        return valueA * valueB / 100;
    }
}
