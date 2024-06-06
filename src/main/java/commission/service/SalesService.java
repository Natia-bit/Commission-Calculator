package commission.service;

import commission.entity.Sale;

import java.util.List;

public interface SalesService {
    List<Sale> findAll();
     void addSale(Sale sale);




}
