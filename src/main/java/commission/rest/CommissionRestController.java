package commission.rest;

import commission.entity.Sale;
import commission.service.SaleServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Optional<Sale> findSaleById(@PathVariable long id){
        return saleService.findSaleById(id);
    }

    @GetMapping("/sales/id/{id}")
    public Sale findById(@PathVariable long id){
        return saleService.findById(id);
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
        saleService.updateSalePercentage(id, sale);
    }
}
