package commission.rest;

import commission.entity.Person;
import commission.entity.Sale;
import commission.service.PersonServiceImpl;
import commission.service.SaleServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CommissionRestController {

    private final SaleServiceImpl saleService;
    private final PersonServiceImpl personService;


    public CommissionRestController(SaleServiceImpl saleService, PersonServiceImpl personService) {
        this.saleService = saleService;
        this.personService = personService;
    }

    @GetMapping("/sales")
    public List<Sale> getSales(){
        return saleService.findAll();
    }

    @GetMapping("/sales/{id}")
    public Optional<Sale> findSaleById(@PathVariable long id){
        return saleService.findSaleById(id);
    }

    @PostMapping("/sales")
    public void createSale(@RequestBody Sale sale){
        saleService.insertSale(sale);
    }

    @DeleteMapping("/sales/{id}")
    public void deleteSale(@PathVariable long id){
        saleService.deleteSale(id);
    }

    @PutMapping("/sales/{id}")
    public void updateSale(@PathVariable long id, @RequestBody Sale sale){
        saleService.updateSale(id, sale);
    }

    @GetMapping("/people")
    public List<Person> getPeople(){
        return personService.findAll();
    }

    @GetMapping("/people/{id}")
    public Optional<Person> findPersonById(@PathVariable long id){
        return personService.findById(id);
    }

    @PostMapping("/people")
    public void insertPerson(@RequestBody Person person){
        personService.insert(person);
    }




}
