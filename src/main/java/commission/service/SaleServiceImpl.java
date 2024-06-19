package commission.service;

import commission.dao.SaleDao;
import commission.dao.SaleDaoImpl;
import commission.entity.Sale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SalesService{

    private final SaleDao saleDao;
    private final Log logger = LogFactory.getLog(this.getClass());

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
        saleDao.insertSale(sale, commissionPerSale(sale));
    }


    @Override
    public Optional<Sale> findSaleById(long id) {
        try{
            return saleDao.findSaleById(id);
        } catch (EmptyResultDataAccessException e){
            logger.error("ID " + id + " not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public boolean deleteSale(long id) {
        boolean isDeleted = false;
        var temp = findSaleById(id);
        if (temp.isEmpty()){
            logger.error("ID " + id + " not found");
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
            logger.error("ID " + id + " not found");
        } else {
            saleDao.updateSale(id, sale, commissionPerSale(sale));
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean updateSalePrice(long id, Sale sale) {
        boolean isUpdated = false;
        var temp = findSaleById(id);
        if (temp.isEmpty()) {
            logger.error("ID " + id + " not found");
        } else {
            var currentPercentage = temp.get().percentage();
            saleDao.updateSalePrice(id, sale, commissionPerSale(sale.price(), currentPercentage));
            isUpdated = true;
        }


        return isUpdated;
    }

    @Override
    public boolean updateSalePercentage(long id, Sale sale) {
        boolean isUpdated = false;
        var temp = findSaleById(id);
        if (temp.isEmpty()) {
            logger.error("ID not found");
        } else {
            var currentPrice = temp.get().price();
            saleDao.updateSalePercentage(id, sale, commissionPerSale(currentPrice, sale.percentage()) );
            isUpdated = true;
        }
        return isUpdated;
    }


    public double commissionPerSale(Sale sale){
        return sale.price() * sale.percentage() / 100 ;
    }

    public double commissionPerSale(double valueA, double valueB){
        return valueA * valueB / 100;
    }


}
