package commission.services;

import commission.dao.CrudDao;
import commission.entity.Person;
import commission.service.CrudService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    @Mock
    private CrudDao<Person> personDao;

    @InjectMocks
    private CrudService<Person> personService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
//        when(personDao.).thenReturn();
//        when(personDao.).thenReturn();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void testFindAllPeople(){
        List<Person> people  = new ArrayList<>();
        Person first = new Person("Dwayne", "Rock");
        Person second = new Person("Boris", "Svavol");
        personDao.insert(first);
        personDao.insert(second);

        when(personService.findAll()).thenReturn(people);
    }

    @Test
    void whenInsertingNewPerson_ShouldReturnNewPerson(){
        Person test = new Person("Levi", "Ackerman");
        personService.insert(test);

        when(personService.insert(test)).thenReturn(test);
    }


}
