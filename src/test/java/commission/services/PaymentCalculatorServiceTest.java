package commission.services;

import commission.dao.CommissionDaoImpl;
import commission.dao.SaleDaoImpl;
import commission.entity.Commission;
import commission.entity.Sale;
import commission.service.PaymentCalculatorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static commission.entity.CommissionType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentCalculatorServiceTest {

    @Mock
    private SaleDaoImpl saleDao;
    @Mock
    private CommissionDaoImpl commissionDao;

    @InjectMocks
    private PaymentCalculatorService paymentCalculatorService;

    @BeforeEach
    public void setUp(){
        paymentCalculatorService = new PaymentCalculatorService(saleDao, commissionDao);

        when(saleDao.findById(1)).thenReturn(Optional.empty());
        when(saleDao.findById(2)).thenReturn(Optional.of(new Sale(2, 2000, OffsetDateTime.parse("2024-01-10T10:15:30+02:00") , 12)));
        when(saleDao.findById(3)).thenReturn(Optional.of(new Sale(3, 3000, OffsetDateTime.parse("2024-02-03T10:15:30+02:00"), 12)));
        when(saleDao.findById(4)).thenReturn(Optional.of(new Sale(4, 1000, OffsetDateTime.parse("2024-03-22T15:36:48+02:00"), 13)));
        when(saleDao.findById(5)).thenReturn(Optional.of(new Sale(5, 7000, OffsetDateTime.parse("2024-03-22T15:36:48+02:00"), 13)));
        when(saleDao.findById(6)).thenReturn(Optional.of(new Sale(6, 14000, OffsetDateTime.parse("2024-04-22T15:36:48+02:00"), 13)));
        when(saleDao.findById(7)).thenReturn(Optional.of(new Sale(7, 5000, OffsetDateTime.parse("2024-05-22T15:36:48+02:00"), 13)));
        when(saleDao.findById(8)).thenReturn(Optional.of(new Sale(8, 10000, OffsetDateTime.parse("2024-06-22T15:36:48+02:00"), 13)));
        when(saleDao.findById(10)).thenReturn(Optional.of(new Sale(10, 0, OffsetDateTime.parse("2024-12-10T10:15:30+02:00") , 20)));

        when(commissionDao.findById(1)).thenReturn(Optional.empty());
        when(commissionDao.findById(2)).thenReturn(Optional.of(new Commission(2, STRAIGHT, 200, 2)));
        when(commissionDao.findById(3)).thenReturn(Optional.of(new Commission(3, GROSS_MARGIN, 120, 3)));
        when(commissionDao.findById(4)).thenReturn(Optional.of(new Commission(4, TIERED, 100, 4)));
        when(commissionDao.findById(5)).thenReturn(Optional.of(new Commission(5, TIERED, 1050, 5)));
        when(commissionDao.findById(6)).thenReturn(Optional.of(new Commission(6, TIERED, 2800, 6)));
        when(commissionDao.findById(7)).thenReturn(Optional.of(new Commission(7, TIERED, 750, 7)));
        when(commissionDao.findById(8)).thenReturn(Optional.of(new Commission(8, TIERED, 1500, 8)));
        when(commissionDao.findById(10)).thenReturn(Optional.of(new Commission(10, TIERED, 1, 10)));
    }

    @AfterEach
    public void tearDown(){
        reset(saleDao, commissionDao);
    }

    @Test
    public void givenCompleteCommission_WhenSearchingForSaleById_ThenVerifyTheSearch(){
        var result = new Commission(10, STRAIGHT, 200, 2);
        paymentCalculatorService.completeCommission(result);

        verify(saleDao, times(1)).findById(2);
    }


    @Test
    public void givenCalculateCommission_WhenCalculatingWithPriceZero_ThenThrowError(){
        var result =commissionDao.findById(10);
        assertTrue(result.isPresent());
        assertThrows(ArithmeticException.class, () -> paymentCalculatorService.calculateCommissionPayment(result.get()));
        verify(saleDao, times(1)).findById(10);
    }

    @Test
    public void givenCalculateCommissionPayment_WhenCalculatingWithTypeStraight_ThenConfirmTypeStraight(){
        var result = commissionDao.findById(2);

        assertTrue(result.isPresent());
        paymentCalculatorService.calculateCommissionPayment(result.get());

        assertEquals(result.get().commissionType(), STRAIGHT);
        verify(saleDao, times(1)).findById(result.get().salesId());
    }

    @Test
    public void givenCalculateCommissionPayment_WhenCalculatingWithTypeStraight_ThenReturnSamePaymentResult(){
        var result = commissionDao.findById(2);
        var expected = 200;

        assertTrue(result.isPresent());
        paymentCalculatorService.calculateCommissionPayment(result.get());

        assertEquals(expected, result.get().payment());
        verify(saleDao, times(1)).findById(result.get().salesId());
    }

    @Test
    public void givenCalculateCommissionPayment_WhenCalculatingTypeGrossMargin_ThenConfirmTypeGrossMargin(){
        var result = commissionDao.findById(3);

        assertTrue(result.isPresent());
        paymentCalculatorService.calculateCommissionPayment(result.get());

        assertEquals(result.get().commissionType(), GROSS_MARGIN);
        verify(saleDao, times(1)).findById(result.get().salesId());
    }

    @Test
    public void givenCalculateCommissionPayment_WhenCalculatingTypeGrossMargin_ThenReturnSamePaymentResult(){
        var result = commissionDao.findById(3);
        // Margin=40, price=3000, percentage = 10 -> (((3000*40) / 100 ) * 10)/100
        var expected = 120;

        assertTrue(result.isPresent());
        paymentCalculatorService.calculateCommissionPayment(result.get());

        assertEquals(expected, result.get().payment());
        verify(saleDao, times(1)).findById(result.get().salesId());
    }

    @Test
    public void givenCalculateCommissionPayment_WhenCalculatingTypeTiered_ThenConfirmTypeTiered(){
        var result = commissionDao.findById(4);

        assertTrue(result.isPresent());
        paymentCalculatorService.calculateCommissionPayment(result.get());

        assertEquals(result.get().commissionType(), TIERED);
        verify(saleDao, times(1)).findById(result.get().salesId());
    }

    @Test
    public void givenCalculateCommissionPayment_WhenCalculatingTypeTierOne_ThenReturnSamePaymentResult(){
        var result = commissionDao.findById(4);
        // Price: 1000 (price < 5000 -> (800*10)/100 = 100
        var expected = 100;

        assertTrue(result.isPresent());
        paymentCalculatorService.calculateCommissionPayment(result.get());

        assertEquals(expected, result.get().payment());
        verify(saleDao, times(1)).findById(result.get().salesId());
    }

    @Test
    public void givenCalculateCommissionPayment_WhenCalculatingTypeTierTwo_ThenReturnSamePaymentResult(){
        var result = commissionDao.findById(5);
        // price = 7000 -> price >= 5000 && price < 10000 -> 7000 * 15 / 100 = 1050
        var expected = 1050;

        assertTrue(result.isPresent());
        paymentCalculatorService.calculateCommissionPayment(result.get());

        assertEquals(expected, result.get().payment());
        verify(saleDao, times(1)).findById(result.get().salesId());
    }

    @Test
    public void givenCalculateCommissionPayment_WhenCalculatingTypeTierTwoEdgeCaseOne_ThenReturnSamePaymentResult(){
        var result = commissionDao.findById(7);
        // price = 5000 -> price >= 5000 && price <= 10000 -> 5000 * 15 / 100 = 750
        var expected = 750;

        assertTrue(result.isPresent());
        paymentCalculatorService.calculateCommissionPayment(result.get());

        assertEquals(expected, result.get().payment());
        verify(saleDao, times(1)).findById(result.get().salesId());
    }
    @Test
    public void givenCalculateCommissionPayment_WhenCalculatingTypeTierTwoEdgeCaseTwo_ThenReturnSamePaymentResult(){
        var result = commissionDao.findById(8);
        // price = 10000 -> price >= 5000 && price <= 10000 -> 10000 * 15 / 100 = 1500
        var expected = 1500;

        assertTrue(result.isPresent());
        paymentCalculatorService.calculateCommissionPayment(result.get());

        assertEquals(expected, result.get().payment());
        verify(saleDao, times(1)).findById(result.get().salesId());
    }

    @Test
    public void givenCalculateCommissionPayment_WhenCalculatingTypeTierThree_ThenReturnSamePaymentResult(){
        var result = commissionDao.findById(6);
        // price = 14,000 -> price > 10,000 -> 14,000 * 20 / 100 = 2800
        var expected = 2800;

        assertTrue(result.isPresent());
        paymentCalculatorService.calculateCommissionPayment(result.get());

        assertEquals(expected, result.get().payment());
        verify(saleDao, times(1)).findById(result.get().salesId());
    }
}
