package commission.services;

import commission.dao.CommissionDaoImpl;
import commission.entity.Commission;
import commission.entity.CommissionType;
import commission.service.CommissionServiceImpl;
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
public class CommissionServiceTest {

    @Mock
    private CommissionDaoImpl commissionDao;

    @InjectMocks
    private CommissionServiceImpl commissionService;

    @BeforeEach
    public void setUp(){
        when(commissionDao.findById(1)).thenReturn(Optional.empty());
        when(commissionDao.findById(2)).thenReturn(Optional.of(new Commission(2, CommissionType.STRAIGHT, 200, 1)));
        when(commissionDao.findById(3)).thenReturn(Optional.of(new Commission(3, CommissionType.TIERED, 300, 2)));
        when(commissionDao.findById(4)).thenReturn(Optional.of(new Commission(4, CommissionType.GROSS_MARGIN, 400, 3)));
    }

    @AfterEach
    public void tearDown(){
        Mockito.reset(commissionDao);
    }

    @Test
    public void givenFindALl_whenDaoReturnsMultipleRecords_ThenReturnMultipleRecords(){
        List<Commission> expected = new ArrayList<>();
        expected.add(new Commission(1, CommissionType.STRAIGHT, 200, 1 ));
        expected.add(new Commission(2, CommissionType.GROSS_MARGIN, 350, 2));
        expected.add(new Commission(3, CommissionType.STRAIGHT, 100, 3));

        when(commissionDao.findAll()).thenReturn(expected);
        var actual = commissionService.findAll();

        assertEquals(expected, actual);
        verify(commissionDao, times(1)).findAll();
    }

    @Test
    public void givenInsert_WhenDaoInsertsNewRecord_thenReturnNewRecord(){
        Commission commission = new Commission(10, CommissionType.STRAIGHT, 100, 1);

        commissionService.insert(commission);
        verify(commissionDao, times(1)).insert(commission);
    }

    @Test
    public void givenFindById_whenSearchWithId_thenReturnRecordOfThatId(){
        var result = commissionService.findById(2);
        assertTrue(result.isPresent());

        verify(commissionDao, times(1)).findById(2);
    }

    @Test
    public void givenFindById_whenSearchWithInvalidId_thenReturnError(){
        assertThrows(ResponseStatusException.class, () -> commissionService.findById(1));
        verify(commissionDao, times(1)).findById(1);
    }

    @Test
    public void givenDelete_whenDeleteRecord_thenReturnTrue(){
        assertTrue(commissionService.delete(2));
        verify(commissionDao, times(1)).delete(2);
    }

    @Test
    public void givenDelete_whenDaoDeleteRecordWithInvalidId_thenReturnError(){
        commissionDao.delete(1);
        assertThrows(ResponseStatusException.class, () -> commissionService.delete(1));
        verify(commissionDao, times(1)).delete(1);
    }

    @Test
    public void givenUpdate_whenDaoUpdateRecord_thenReturnUpdatedRecord(){
        var newCommissionData = new Commission(2, CommissionType.STRAIGHT, 1000, 1);

        commissionService.update(2, newCommissionData);
        verify(commissionDao, times(1)).update(2, newCommissionData);
    }

}
