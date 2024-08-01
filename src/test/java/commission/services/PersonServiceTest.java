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
    void whenSearchingForAll_findAll_ThenReturnAll(){
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
    public void whenAddingNewPerson_Insert_ThenReturnNewPerson(){
        Person person = new Person(100, "Levi", "Ackerman");
        personService.insert(person);
        verify(personDao, times(1)).insert(person);
    }

    @Test
    public void whenSearchingWithValidId_FindById_ThenRerunTheResult(){
        var result = personService.findById(2);

        assertTrue(result.isPresent());
        assertEquals("Levi", result.get().firstName());
        assertEquals("Ackerman", result.get().lastName());
        assertEquals(2, result.get().id());

        verify(personDao, times(1)).findById(2);
    }


    @Test
    public void whenSearchingAnInvalidId_FindById_thenRerunErrorThrown(){
        assertThrows(ResponseStatusException.class, () -> personService.findById(1));
        verify(personDao, times(1)).findById(1);
    }

    @Test
    public void whenSearchingAnInvalidId_FindById_thenRerunCustomError(){
        when(personDao.findById(1)).thenThrow(new RuntimeException("DB doesn't work"));
        assertThrows(RuntimeException.class, () -> personService.findById(1));
        verify(personDao, times(1)).findById(1);
    }

    @Test
    public void whenDeletingWithId_Delete_ThenReturnTrue(){
        var result = personService.delete(2);
        assertTrue(result);
        verify(personDao, times(1)).delete(2);
    }

    @Test
    public void whenDeletingWithInvalidId_Delete_ThenReturnError(){
        personDao.delete(1);
        assertThrows(ResponseStatusException.class, () -> personService.delete(1));
        verify(personDao, times(1)).delete(1);
    }

    @Test
    public void whenUpdatingPersonById_Update_ThenReturnWithNewName(){
        var newData = new Person(3, "Nico", "Robin");
        personService.update(3, newData);

        assertEquals("Nico", newData.firstName());
        assertEquals("Robin", newData.lastName());
        assertEquals(3, newData.id());

        verify(personDao, times(1)).update(3, newData);
    }

    @Test
    public void whenUpdatingWithInvalidId_Update_ThenReturnError(){
        var newData = new Person(1, "Nico", "Robin");

        personDao.update(1,newData);

        assertThrows(ResponseStatusException.class, () -> personService.update(1,newData));
        verify(personDao, times(1)).update(1, newData);
    }

}
