package commission.service;

import commission.dao.SaleDaoImpl;
import commission.entity.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SalesService{

    private final SaleDaoImpl saleDao;

    @Autowired
    public SaleServiceImpl(SaleDaoImpl saleDao) {
        this.saleDao = saleDao;
    }

    @Override
    public List<Sale> findAll(){
        return saleDao.findAll();
    }
    @Override
    public void insertSale(Sale sale){
        saleDao.insertSale(sale);
    }

    @Override
    public Optional<Sale> findSaleById(long id) {
        // checks
        return Optional.of(saleDao.findSaleById(id));
    }


    @Override
    public void deleteSale(long id) {
        // checks
            saleDao.deleteSale(id);
            System.out.println("Deleted");

    }
}
