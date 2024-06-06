package commission.rest;

import commission.entity.Sale;
import commission.service.SaleServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommissionRestController {


    private final SaleServiceImpl saleService;

    public CommissionRestController(SaleServiceImpl saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/test")
    public String sayHello(){
        return "Hello";
    }

    @GetMapping("/sales")
    public List<Sale> getSales(){
        return saleService.findAll();
    }


    @PostMapping("/sales")
    public void addSale(@RequestBody Sale sale){
        saleService.addSale(sale);
    }
}
