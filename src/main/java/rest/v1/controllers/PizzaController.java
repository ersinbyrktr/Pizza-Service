package rest.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rest.v1.pojos.Location;
import rest.v1.pojos.Pizza;
import rest.v1.pojos.Size;
import rest.v1.pojos.Topping;
import rest.v1.repos.PizzaRepository;
import rest.v1.repos.ToppingRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@CrossOrigin(origins = "*")
@RestController    // This means that this class is a Controller
@RequestMapping(path="v1/pizza") // This means URL's start with /demo (after Application path)
public class PizzaController {

    private final ToppingRepository toppingRepository;
    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaController(ToppingRepository toppingRepository, PizzaRepository pizzaRepository) {
        this.toppingRepository = toppingRepository;
        this.pizzaRepository = pizzaRepository;
    }

    @RequestMapping( method = POST) // Map ONLY GET Requests
	public @ResponseBody ResponseEntity addNewPizza (@RequestParam String name,
                                                     @RequestParam Size size,
                                                     @RequestParam Double price,
                                                     HttpServletRequest request) {
		Pizza newPizza = new Pizza(name, size);
		pizzaRepository.save(newPizza);
        return new ResponseEntity<>(newPizza.getLocation(request.getRequestURL().toString()), HttpStatus.CREATED);
	}
	
	@RequestMapping( method= GET)
	public @ResponseBody
    ResponseEntity<List<Integer>> getAllPizzas() {
	    List<Integer> result = pizzaRepository.findPizzaIds();
	    if(result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
	        return new ResponseEntity<>(result, HttpStatus.OK);
        }
	}

    @RequestMapping(value = "{pizzaId:[0-9A-Za-z]+}", method = GET)
    public @ResponseBody ResponseEntity<Pizza> getPizzaById(@PathVariable("pizzaId")  Integer pizzaId) {
        Pizza current_pizza = pizzaRepository.findById(pizzaId);
        if(current_pizza == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(current_pizza, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "{pizzaId:[0-9A-Za-z]+}", method = PUT)
    public @ResponseBody ResponseEntity<String> updatePizza(
                                            @PathVariable("pizzaId")  Integer pizzaId,
                                            @RequestParam String name,
                                            @RequestParam Size size,
                                            HttpServletResponse res) {
        Pizza current_pizza = pizzaRepository.findById(pizzaId);
        if (current_pizza == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        current_pizza.setName(name);
        current_pizza.setSize(size);
        current_pizza.setPrice();
        pizzaRepository.save(current_pizza);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "{pizzaId:[0-9A-Za-z]+}", method = DELETE)
    public @ResponseBody ResponseEntity deletePizzaById(@PathVariable("pizzaId")  Integer pizzaId) {
	    try {
            pizzaRepository.delete(pizzaId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (EmptyResultDataAccessException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    // Toppings

    @RequestMapping(value="{pizzaId:[0-9A-Za-z]+}/topping/", method = POST) // Map ONLY GET Requests
    public @ResponseBody ResponseEntity<Location> addNewPizza (@PathVariable("pizzaId")  Integer pizzaId,
                                                               @RequestParam String name,
                                                               @RequestParam Double price,
                                                               HttpServletRequest request) {
        Pizza current_pizza = pizzaRepository.findById(pizzaId);
        if (current_pizza == null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        Topping n = new Topping(name, price);
        toppingRepository.save(n);
        Set<Topping> toppings = current_pizza.getToppings();
        toppings.add(n);
        current_pizza.setToppings(toppings);
        pizzaRepository.save(current_pizza);
        return new ResponseEntity<>(n.getLocation(request.getRequestURL().toString()), HttpStatus.CREATED);
    }

    @RequestMapping(value="{pizzaId:[0-9A-Za-z]+}/topping/", method = GET) // Map ONLY GET Requests
    public @ResponseBody
    ResponseEntity<Set<Integer>> getToppingsOfPizza (@PathVariable("pizzaId")  Integer pizzaId) {
        Pizza current_pizza = pizzaRepository.findById(pizzaId);
        Set<Integer> toppingIds = current_pizza.getToppingIds();
        if(toppingIds.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(toppingIds, HttpStatus.OK);
        }
    }


    @RequestMapping(value="{pizzaId:[0-9A-Za-z]+}/topping/{toppingId:[0-9A-Za-z]+}", method = GET) // Map ONLY GET Requests
    public @ResponseBody
    ResponseEntity<Topping> getToppingById (@PathVariable("pizzaId")  Integer pizzaId,
                                            @PathVariable("toppingId")  Integer toppingId) {
        Pizza current_pizza = pizzaRepository.findById(pizzaId);
        Topping current_topping = current_pizza.getToppingById(toppingId);
        if (current_topping == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(current_topping, HttpStatus.OK);
        }
    }


    @RequestMapping(value="{pizzaId:[0-9A-Za-z]+}/topping/{toppingId:[0-9A-Za-z]+}", method = DELETE) // Map ONLY GET Requests
    public @ResponseBody
    ResponseEntity<String> deleteToppingById (@PathVariable("pizzaId")  Integer pizzaId,
                                              @PathVariable("toppingId")  Integer toppingId) {
        Pizza current_pizza = pizzaRepository.findById(pizzaId);
        if (current_pizza.deleteToppingById(toppingId)){
            pizzaRepository.save(current_pizza);
            toppingRepository.delete(toppingId);
            return new ResponseEntity<>("OK", HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Exception Handling

    @ExceptionHandler({org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity resolveBadRequestException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
