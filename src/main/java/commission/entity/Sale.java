package commission.entity;

import java.sql.Timestamp;

public record Sale (long id, double price, Timestamp dateStamp, long personId) {

 }
