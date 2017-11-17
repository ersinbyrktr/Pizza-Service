package rest.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.pojos.Pizza;
import rest.pojos.Topping;
import rest.repos.PizzaRepository;
import rest.repos.ToppingRepository;

import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController    // This means that this class is a Controller
@RequestMapping(path="/pizza/{pizzaId:[0-9]+}/topping") // This means URL's start with /demo (after Application path)
public class ToppingController {

    private final ToppingRepository toppingRepository;
    private final PizzaRepository pizzaRepository;

    @Autowired
    public ToppingController(ToppingRepository toppingRepository, PizzaRepository pizzaRepository) {
        this.toppingRepository = toppingRepository;
        this.pizzaRepository = pizzaRepository;
    }


    @RequestMapping(value="/", method = POST) // Map ONLY GET Requests
    public @ResponseBody String addNewPizza (@PathVariable("pizzaId")  Integer pizzaId,
                                             @RequestParam String name,
                                             @RequestParam Double price) {
        Topping n = new Topping(name, price);
        toppingRepository.save(n);
        Pizza current_pizza = pizzaRepository.findById(pizzaId);
        Set<Topping> toppings = current_pizza.getToppings();
        toppings.add(n);
        current_pizza.setToppings(toppings);
        pizzaRepository.save(current_pizza);
        return "Saved";
    }

    @RequestMapping(value="/", method = GET) // Map ONLY GET Requests
    public @ResponseBody
    ResponseEntity<Set<Integer>> getToppingsOfPizza (@PathVariable("pizzaId")  Integer pizzaId) {
        Pizza current_pizza = pizzaRepository.findById(pizzaId);
        Set<Integer> toppingIds = current_pizza.getToppingIds();
        return new ResponseEntity<>(toppingIds, HttpStatus.OK);
    }


    @RequestMapping(value="/{toppingId:[0-9]+}", method = GET) // Map ONLY GET Requests
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


    @RequestMapping(value="/{toppingId:[0-9]+}", method = DELETE) // Map ONLY GET Requests
    public @ResponseBody
    ResponseEntity<String> deleteToppingById (@PathVariable("pizzaId")  Integer pizzaId,
                                            @PathVariable("toppingId")  Integer toppingId) {
        Pizza current_pizza = pizzaRepository.findById(pizzaId);
        if (current_pizza.deleteToppingById(toppingId)){
            pizzaRepository.save(current_pizza);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
