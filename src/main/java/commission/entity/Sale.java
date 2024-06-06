package commission.entity;


import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
 public record Sale (long id, double transaction) {

  public Sale{};

 }
