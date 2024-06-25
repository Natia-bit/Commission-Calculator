package commission.entity;

import java.sql.Timestamp;

public record Sale (long id, Timestamp dateStamp, long personId) {

 }
