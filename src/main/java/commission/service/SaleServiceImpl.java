package commission.service;

import commission.dao.SaleDao;
import commission.dao.SaleDaoImpl;
import commission.entity.Sale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        saleDao.insert(sale);
        logger.info("New sale created");
    }


    @Override
    public Optional<Sale> findSaleById(long id) {
        var temp = saleDao.findById(id);
        if (temp.isPresent()){
            return temp;
        } else {
            logger.error("ID " + id + " not found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public boolean deleteSale(long id) {
        boolean isDeleted = false;
        var temp = findSaleById(id);
        if(temp.isPresent()){
            saleDao.delete(id);
            logger.info("Sale ID " + id + " deleted");
            isDeleted = true;
        }

        return isDeleted;
    }

    @Override
    public boolean updateSale(long id, Sale sale) {
        boolean isUpdated = false;
        var temp = findSaleById(id);
        if(temp.isPresent()){
            saleDao.update(id, sale);
            logger.info("Sale ID " + id + " updated");
            isUpdated = true;
        }

        return isUpdated;
    }
}
