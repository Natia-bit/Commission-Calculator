package commission.entity;

public record Commission(long id, CommissionType commissionType, double payment, long salesId ) {

}