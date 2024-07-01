package commission.entity;

public record Commission(long id, Type type, double payment, long salesId ) {
}