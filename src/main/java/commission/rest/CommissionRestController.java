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

    @GetMapping("/sales")
    public List<Sale> getSales(){
        return saleService.findAll();
    }

    @PostMapping("/sales")
    public void createSale(@RequestBody Sale sale){
        saleService.insertSale(sale);
    }


    @GetMapping("/sales/{id}")
    public void findSaleById(@PathVariable long id){
        saleService.findSaleById(id);
    }

    @DeleteMapping("/sales/{id}")
    public void deleteSale(@PathVariable long id){
        saleService.deleteSale(id);
    }

    @PutMapping("/sales/{id}")
    public void updateSale(@PathVariable long id, @RequestBody Sale sale){
        saleService.updateSale(id, sale);
    }

    @PutMapping("/sales/price/{id}")
    public void updateSalePrice(@PathVariable long id, @RequestBody Sale sale){
        saleService.updateSalePrice(id, sale);
    }

    @PutMapping("/sales/percentage/{id}")
    public void updateSalePercentage(@PathVariable long id, @RequestBody Sale sale){
        System.out.println(sale);
        saleService.updateSalePercentage(id, sale);
    }

}
