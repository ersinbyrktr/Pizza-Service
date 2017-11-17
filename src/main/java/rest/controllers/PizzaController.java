package rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rest.pojos.Pizza;
import rest.repos.PizzaRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController    // This means that this class is a Controller
@RequestMapping(path="/pizza") // This means URL's start with /demo (after Application path)
public class PizzaController {

	private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @RequestMapping(value="/", method = POST) // Map ONLY GET Requests
	public @ResponseBody String addNewPizza (@RequestParam String name,
                                             @RequestParam String size,
                                             @RequestParam Double price) {
		Pizza n = new Pizza(name, size, price);
		pizzaRepository.save(n);
		return "Saved";
	}
	
	@RequestMapping(value="/", method= GET)
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

    @RequestMapping(value = "{pizzaId:[0-9]+}", method = GET)
    public @ResponseBody ResponseEntity<Pizza> getPizzaById(@PathVariable("pizzaId")  Integer pizzaId) {
        Pizza current_pizza = pizzaRepository.findById(pizzaId);
        return new ResponseEntity<>(current_pizza, HttpStatus.OK);
    }

    @RequestMapping(value = "{pizzaId:[0-9]+}", method = PUT)
    public @ResponseBody ResponseEntity<String> updatePizza(
                                            @PathVariable("pizzaId")  Integer pizzaId,
                                            @RequestParam String name,
                                            @RequestParam String size,
                                            @RequestParam Double price,
                                            HttpServletResponse res) {
        Pizza current_pizza = pizzaRepository.findById(pizzaId);
        current_pizza.setName(name);
        current_pizza.setSize(size);
        current_pizza.setPrice(price);
        pizzaRepository.save(current_pizza);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "{pizzaId:[0-9]+}", method = DELETE)
    public @ResponseBody ResponseEntity deletePizzaById(@PathVariable("pizzaId")  Integer pizzaId) {
	    try {
            pizzaRepository.delete(pizzaId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (EmptyResultDataAccessException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
