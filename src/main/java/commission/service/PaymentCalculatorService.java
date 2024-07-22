package commission.service;

import commission.dao.CrudDao;
import commission.entity.Commission;
import commission.entity.Sale;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentCalculatorService {

    private  final CrudDao<Sale> saleDao;
    private final CrudDao<Commission> commissionDao;

    public PaymentCalculatorService(CrudDao<Sale> saleDao, CrudDao<Commission> commissionDao) {
        this.saleDao = saleDao;
        this.commissionDao = commissionDao;
    }

    public void completeCommission(Commission commission){
        Optional<Sale> sale = this.saleDao.findById(commission.salesId());
        Commission completeCommission = new Commission(
                commission.id(),
                commission.commissionType(),
                calculateCommissionPayment(commission),
                commission.salesId());

       this.commissionDao.insert(completeCommission);
    }

    public double calculateCommissionPayment(Commission commission){
        Optional<Sale> sale = this.saleDao.findById(commission.salesId());
        var price = sale.get().price();
        System.out.println(price);
        double payment = 1;
        var percentage = 10;
        var margin = 10;

        switch (commission.commissionType()){
            case STRAIGHT -> {
                payment = price * percentage / 100;
            }
            case GROSS_MARGIN -> {
                payment =  ((price * margin ) / 100 ) * 10;
            }
            case TIERED -> {
                if (price <= 1000){
                    payment =   price * 10 / 100;
                } else if (price >= 5000 && price <10000){
                    payment = price * 15 / 100;
                } else if (price > 10000){
                    payment = price * 20 / 100;
                }
            }
        }

        return payment;
    }

}
