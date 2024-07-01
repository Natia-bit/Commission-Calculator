package commission.entity;

public record Commission(long id, CommissionType type, double payment, long salesId ) {

}