package commission.services;

import commission.dao.CrudDao;
import commission.entity.Person;
import commission.service.CrudService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static java.util.zip.Inflater.reset;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    @Mock
    private CrudDao<Person> personDao;

    @InjectMocks
    private CrudService<Person> personService;

    @BeforeEach
    public void setUp() {
        when(personDao.).thenReturn();
        when(personDao.).thenReturn();
    }

    @AfterEach
    public void tearDown() {
    }


}
