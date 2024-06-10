package commission.service;

import commission.dao.SaleDaoImpl;
import commission.entity.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        try {
            return Optional.of(saleDao.findSaleById(id));
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("ID does not exists");
        }
    }

    @Override
    public boolean deleteSale(long id) {
        boolean isDeleted = false;
        var temp = findSaleById(id);
        if (temp.isEmpty()){
            System.out.println("ID does not exists");
        } else {
            saleDao.deleteSale(id);
            isDeleted = true;
        }

        return isDeleted;
    }


    @Override
    public boolean updateSale(long id, Sale sale) {
        boolean isUpdated = false;
        var temp = findSaleById(id);
        if (temp.isEmpty()) {
            System.out.println("ID does not exists");
        } else {
            saleDao.updateSale(id, sale);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean updateSalePrice(long id, Sale sale) {
        boolean isUpdated = false;
        var temp = findSaleById(id);
        if (temp.isEmpty()) {
            System.out.println("ID does not exists");
        } else {
            saleDao.updateSalePrice(id, sale);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean updateSalePercentage(long id, Sale sale) {
        boolean isUpdated = false;
        var temp = findSaleById(id);
        if (temp.isEmpty()) {
            System.out.println("ID does not exists");
        } else {
            saleDao.updateSalePercentage(id, sale);
            isUpdated = true;
        }
        return isUpdated;
    }
}
