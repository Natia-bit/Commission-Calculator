package commission.dao;

import commission.entity.Sale;

import java.util.List;

public interface SaleDao {

    List<Sale> findAll();



    void insertSale(Sale sale);


//    boolean deleteSale(int id);


}
