package commission.service;

import commission.entity.Sale;

import java.util.List;
import java.util.Optional;

public interface SalesService {
    List<Sale> findAll();
     void insertSale(Sale sale);
     Optional<Sale> findSaleById(long id);
     boolean deleteSale(long id);
     boolean updateSale(long id, Sale sale);

    boolean updateSalePrice(long id, Sale sale);
    boolean updateSalePercentage(long id, Sale sale);









}
