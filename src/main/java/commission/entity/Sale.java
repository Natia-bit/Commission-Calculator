package commission.entity;

import java.time.Instant;

public record Sale (long id, double price, Instant dateStamp, long personId) {

 }
