package commission.service;

import commission.dao.CrudDao;
import commission.entity.Person;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements CrudService<Person> {

    private final CrudDao<Person> personDao;
    private final Log logger = LogFactory.getLog(this.getClass());


    public PersonServiceImpl(CrudDao<Person> personDao) {
        this.personDao = personDao;
    }

    @Override
    public List<Person> findAll() {
        return personDao.findAll();
    }

    @Override
    public void insert(Person person) {
        personDao.insert(person);
        logger.info("New person added");
    }

    @Override
    public Optional<Person> findById(long id) {
        var temp = personDao.findById(id);
        if (temp.isPresent()){
            return temp;
        } else {
            logger.error("ID not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public boolean delete(long id) {
        boolean isDeleted = false;

        var temp = findById(id);
        if (temp.isPresent()){
            personDao.delete(id);
            logger.info("Person " + temp.get().firstName() + "deleted");
            isDeleted = true;
        }

        return isDeleted;
    }

    @Override
    public boolean update(long id, Person person) {
        boolean isUpdated = false;
        var temp = findById(id);
        if (temp.isPresent()){
            personDao.update(id, person);
            logger.info("Person updated");
            isUpdated = true;
        }
        return isUpdated;
    }

}
