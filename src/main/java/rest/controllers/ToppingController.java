package rest.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.pojos.Pizza;
import rest.pojos.Topping;
import rest.repos.PizzaRepository;
import rest.repos.ToppingRepository;

import java.util.List;
import java.util.Set;

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
                                             @RequestParam Integer id,
                                             @RequestParam String name,
                                             @RequestParam Double price) {
        Topping n = new Topping(id, name, price);
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
    ResponseEntity<List<Integer>> getToppingsOfPizza (@PathVariable("pizzaId")  Integer pizzaId) {
        List<Integer> toppingIds = toppingRepository.findToppingIds(pizzaId);
        return new ResponseEntity<>(toppingIds, HttpStatus.OK);
    }
}
