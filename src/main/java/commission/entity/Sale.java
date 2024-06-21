package commission.entity;

 public record Sale (long id, double price, double percentage, double commission) {


  public double commission() {
   return this.price() * this.percentage() / 100;
  }
 }
