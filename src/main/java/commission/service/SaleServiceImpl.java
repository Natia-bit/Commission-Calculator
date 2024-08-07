package commission.service;

import commission.dao.CrudDao;
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
public class SaleServiceImpl implements CrudService<Sale> {

    private final CrudDao<Sale> saleDao;
    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    public SaleServiceImpl(CrudDao<Sale> saleDao) {
        this.saleDao = saleDao;
    }

    @Override
    public List<Sale> findAll(){
        return saleDao.findAll();
    }
    @Override
    public void insert(Sale sale){
        saleDao.insert(sale);
        logger.info("New sale created");
    }

    @Override
    public Optional<Sale> findById(long id) {
        var temp = saleDao.findById(id);
        if (temp.isEmpty()){
            logger.error("ID " + id + " not found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return temp;
    }

    @Override
    public boolean delete(long id) {
        boolean isDeleted = false;
        var temp = findById(id);
        if(temp.isPresent()){
            saleDao.delete(id);
            logger.info("Sale ID " + id + " deleted");
            isDeleted = true;
        }

        return isDeleted;
    }

    @Override
    public boolean update(long id, Sale sale) {
        boolean isUpdated = false;
        var temp = findById(id);
        if(temp.isPresent()){
            saleDao.update(id, sale);
            logger.info("Sale ID " + id + " updated");
            isUpdated = true;
        }

        return isUpdated;
    }
}
