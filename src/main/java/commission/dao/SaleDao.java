package commission.dao;

import commission.entity.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleDao extends SalesCommissionDao<Sale> {
//    List<Sale> findAll();
//    void insertSale(Sale sale);
//    Optional<Sale> findSaleById(long id);
//    void deleteSale(long id);
//    void updateSale(long id, Sale sale);
//    void updateSalePrice(long id, Sale sale);
//    void updateSalePercentage(long id, Sale sale);


    @Override
    List<Sale> findAll();

    @Override
    Optional<Sale> findById(long id);

    @Override
    void insert(Sale sale);

    @Override
    void update(long id, Sale sale);

    @Override
    void delete(long id);
}
