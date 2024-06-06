package commission.dao;

import commission.entity.Sale;

import java.util.List;

public interface SaleDao {

    List<Sale> findAll();
    int addSale(Sale sale);

}
