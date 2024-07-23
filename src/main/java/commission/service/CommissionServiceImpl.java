package commission.service;

import commission.dao.CrudDao;
import commission.entity.Commission;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommissionServiceImpl implements CrudService<Commission> {
    public final CrudDao<Commission> commissionDao;
    private final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    public CommissionServiceImpl(CrudDao<Commission> commissionDao) {
        this.commissionDao = commissionDao;
    }

    @Override
    public List<Commission> findAll() {
        return commissionDao.findAll();
    }

    @Override
    public void insert(Commission commission) {
        commissionDao.insert(commission);
        logger.info("Commission created");
    }

    @Override
    public Optional<Commission> findById(long id) {
        var temp = commissionDao.findById(id);
        if(temp.isEmpty()){
            logger.error("ID " + id + " not found.");
        }
        return temp;
    }

    @Override
    public boolean delete(long id) {
        boolean isDeleted = false;
        var temp = findById(id);
        if (temp.isPresent()){
            commissionDao.delete(id);
            logger.info("Commission ID " + id + " deleted");
            isDeleted = true;
        }
        return isDeleted;
    }

    @Override
    public boolean update(long id, Commission commission) {
        return false;
    }

}
