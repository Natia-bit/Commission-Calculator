package commission.services;

import commission.dao.SaleDaoImpl;
import commission.entity.Sale;
import commission.service.SaleServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

    @Mock
    private SaleDaoImpl saleDao;

    @InjectMocks
    private SaleServiceImpl saleService;


    @BeforeEach
    public void setUp(){
        when(saleDao.findById(1)).thenReturn(Optional.empty());
        when(saleDao.findById(2)).thenReturn(Optional.of(
                new Sale(
                        2,
                        200,
                        OffsetDateTime.parse("2024-07-22 13:36:48.401+0200",  DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm:ss.SSSX" )),
                        12)));
        when(saleDao.findById(3)).thenReturn(Optional.of(
                new Sale(
                        3,
                        300,
                        OffsetDateTime.parse("2024-09-22 15:36:48.401+0200", DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm:ss.SSSX" )),
                        13)));
    }

    @AfterEach
    public void tearDown(){
        Mockito.reset(saleDao);
    }

    @Test
    public void whenSearchingForAllSales_findAll_thenReturnAll(){
        List<Sale> expected = new ArrayList<>();

        expected.add(new Sale(
                1,
                100,
                OffsetDateTime.parse("2024-08-18 14:27:15.103+0200", DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm:ss.SSSX" )),
                1));

        expected.add(new Sale(
                2,
                200,
                OffsetDateTime.parse("2024-07-20 12:00:00.000+0200", DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm:ss.SSSX" )),
                2));
        expected.add(new Sale(3,
                300,
                OffsetDateTime.parse("2024-07-30 13:00:00.000+0200", DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm:ss.SSSX" )),
                3));

        when(saleDao.findAll()).thenReturn(expected);
        var actual =  saleDao.findAll();

        assertEquals(expected, actual);
        verify(saleDao, times(1)).findAll();
    }

    @Test
    public void whenAddingNewSale_insert_thenReturnNewSale(){
        Sale sale = new Sale(
                4,
                400,
                OffsetDateTime.parse("2024-04-10 09:00:00.000+0200", DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm:ss.SSSX" )),
                1);

        saleService.insert(sale);
        verify(saleDao, times(1)).insert(sale);
    }


    @Test
    public void whenSearchingForSaleWithId_findById_thenReturnResult(){
        var result = saleService.findById(2);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().id());
        assertEquals(200, result.get().price());
        assertEquals(12, result.get().personId());

        verify(saleDao, times(1)).findById(2);
    }

    @Test
    public void whenSearchingForSaleWithInvalidID_findById_thenReturnError(){
        assertThrows(ResponseStatusException.class, () -> saleService.findById(1));
        verify(saleDao, times(1)).findById(1);
    }

    @Test
    public void  whenDeletingUsingId_delete_thenReturnTrue(){
        assertTrue(saleService.delete(2));
        verify(saleDao, times(1)).delete(2);
    }

    @Test
    public void whenDeletingUsingInvalidId_delete_thenReturnError(){
        saleDao.delete(1);
        assertThrows(ResponseStatusException.class, () -> saleService.delete(1));
        verify(saleDao, times(1)).delete(1);
    }

    @Test
    public void whenUpdatingSale_update_thenReturnWithUpdatedSale(){
        var newSaleData = new Sale(
                3,
                3199,
                OffsetDateTime.parse("2024-08-18 14:27:15.103+0200", DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm:ss.SSSX" )),
                13);

        saleService.update(3, newSaleData);

        assertEquals(3, newSaleData.id());
        assertEquals(3199, newSaleData.price());
        assertEquals(13, newSaleData.personId());

        verify(saleDao, times(1)).update(3, newSaleData);
    }

    @Test
    public void whenUpdatingSaleWithInvalidId_update_thenReturnError(){
        var newSaleData = new Sale(
                1,
                3000000,
                OffsetDateTime.parse("2024-08-18 14:27:15.103+0200", DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm:ss.SSSX" )),
                11);

        saleDao.update(1, newSaleData);

        assertThrows(ResponseStatusException.class, () -> saleService.update(1, newSaleData));
        verify(saleDao, times(1)).update(1, newSaleData);
    }
}
