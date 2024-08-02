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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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





}
