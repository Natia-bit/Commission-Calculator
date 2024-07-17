package commission.entity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static java.lang.Double.parseDouble;

public record Commission(long id, CommissionType commissionType, double payment, long salesId ) {

    public double payment(){
        System.out.println("Calculating payment");
        List<Field> allFields = Arrays.asList(Sale.class.getDeclaredFields());
        System.out.println(allFields);
        var field = allFields.get(2);
        System.out.println(field);
        var price = parseDouble(String.valueOf(field));

        switch (commissionType){
            case STRAIGHT -> {
                return price * 10 / 100 ;
            }
            case GROSS_MARGIN -> {
                var margin = 10;
                return ((price * margin ) / 100 ) * 10;
            }
            case TIERED -> {
                if (price <= 1000){
                    return price * 10 / 100;
                } else if (price >= 5000 && price <10000){
                    return price * 15 / 100;
                } else if (price > 10000){
                    return price * 20 / 100;
                }
            }
        }

        return payment;
    }

}