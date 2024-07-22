package commission.rest;

import commission.entity.Commission;
import commission.entity.Person;
import commission.entity.Sale;
import commission.service.CommissionServiceImpl;
import commission.service.PaymentCalculatorService;
import commission.service.PersonServiceImpl;
import commission.service.SaleServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CommissionRestController {
    private final PersonServiceImpl personService;
    private final SaleServiceImpl saleService;
    private final CommissionServiceImpl commissionService;
    private final PaymentCalculatorService paymentCalculatorService;


    public CommissionRestController(PersonServiceImpl personService,
                                    SaleServiceImpl saleService,
                                    CommissionServiceImpl commissionService,
                                    PaymentCalculatorService paymentCalculatorService) {
        this.personService = personService;
        this.saleService = saleService;
        this.commissionService = commissionService;
        this.paymentCalculatorService = paymentCalculatorService;
    }

    @GetMapping("/sales")
    public List<Sale> getSales(){
        return saleService.findAll();
    }

    @GetMapping("/sales/{id}")
    public Optional<Sale> findSaleById(@PathVariable long id){
        return saleService.findById(id);
    }

    @PostMapping("/sales")
    public void createSale(@RequestBody Sale sale){
        saleService.insert(sale);
    }

    @DeleteMapping("/sales/{id}")
    public void deleteSale(@PathVariable long id){
        saleService.delete(id);
    }

    @PutMapping("/sales/{id}")
    public void updateSale(@PathVariable long id, @RequestBody Sale sale){
        saleService.update(id, sale);
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

    @PutMapping("/people/{id}")
    public void updatePerson(@PathVariable long id,@RequestBody Person person){
        personService.update(id, person);
    }

    @DeleteMapping("/people/{id}")
    public void deletePerson(@PathVariable long id){
        personService.delete(id);
    }


    @PatchMapping("/people/{id}")
    public void partialPersonUpdate(@PathVariable long id, @RequestBody Person partialPerson){
        personService.update(id, partialPerson);
    }


    @PostMapping("/commission")
    public void addCommission(@RequestBody Commission commission){
       paymentCalculatorService.completeCommission(commission);
    }
}
