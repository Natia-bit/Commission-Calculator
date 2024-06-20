package commission.dao;

import commission.entity.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleDao {
    List<Sale> findAll();
    void insertSale(Sale sale);
    Optional<Sale> findSaleById(long id);
    void deleteSale(long id);
    void updateSale(long id, Sale sale);
    void updateSalePrice(long id, Sale sale);
    void updateSalePercentage(long id, Sale sale);
}
