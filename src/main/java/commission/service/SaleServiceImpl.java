package commission.service;

import commission.entity.Sale;
import commission.dao.SaleDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
