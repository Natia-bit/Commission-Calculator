package commission.services;

import commission.dao.PersonDaoImpl;
import commission.entity.Person;
import commission.service.PersonServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonDaoImpl personDao;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    public void setUp() {
        when(personDao.findById(1)).thenReturn(Optional.empty());
        when(personDao.findById(2)).thenReturn(Optional.of(new Person(2, "Levi", "Ackerman")));
        when(personDao.findById(3)).thenReturn(Optional.of(new Person(2, "Mikasa", "Ackerman")));
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(personDao);
    }

    @Test
    void givenFindAll_whenDaoReturnsMultipleRecords_thenReturnMultipleRecords(){
        List<Person> expected  = new ArrayList<>();
        expected.add(new Person(1, "Luffy", "D Monkey"));
        expected.add(new Person(1, "Zorro", "Roronoa"));
        expected.add(new Person(1, "Nami", "Bellemere"));

        when(personDao.findAll()).thenReturn(expected);
        var actual =  personService.findAll();

        assertEquals(expected, actual);
        verify(personDao, times(1)).findAll();
    }

    @Test
    public void givenInsert_whenDaoInsertNewRecord_thenReturnNewRecord(){
        Person person = new Person(100, "Levi", "Ackerman");
        personService.insert(person);
        verify(personDao, times(1)).insert(person);
    }

    @Test
    public void givenFindById_whenDaoSearchWithId_thenReturnRecordOfThatId(){
        var result = personService.findById(2);

        assertTrue(result.isPresent());
        assertEquals("Levi", result.get().firstName());
        assertEquals("Ackerman", result.get().lastName());
        assertEquals(2, result.get().id());

        verify(personDao, times(1)).findById(2);
    }


    @Test
    public void givenFindById_whenDaoSearchWithInvalidId_thenReturnError(){
        assertThrows(ResponseStatusException.class, () -> personService.findById(1));
        verify(personDao, times(1)).findById(1);
    }

    @Test
    public void givenFindById_whenDaoSearchWithInvalidId_thenReturnCustomError(){
        when(personDao.findById(1)).thenThrow(new RuntimeException("DB doesn't work"));
        assertThrows(RuntimeException.class, () -> personService.findById(1));
        verify(personDao, times(1)).findById(1);
    }

    @Test
    public void givenDelete_whenDaoDeleteRecord_thenReturnTrue(){
        var result = personService.delete(2);
        assertTrue(result);
        verify(personDao, times(1)).delete(2);
    }

    @Test
    public void givenDelete_whenDaoDeleteRecordWithInvalidId_thenReturnError(){
        personDao.delete(1);
        assertThrows(ResponseStatusException.class, () -> personService.delete(1));
        verify(personDao, times(1)).delete(1);
    }

    @Test
    public void givenUpdate_whenDaoUpdateRecord_thenReturnUpdatedRecord(){
        var newData = new Person(3, "Nico", "Robin");
        personService.update(3, newData);

        verify(personDao, times(1)).update(3, newData);
    }

    @Test
    public void givenUpdate_whenDaoUpdatePersonWithInvalidId_thenReturnError(){
        var newData = new Person(1, "Nico", "Robin");

        personDao.update(1,newData);

        assertThrows(ResponseStatusException.class, () -> personService.update(1,newData));
        verify(personDao, times(1)).update(1, newData);
    }
}
