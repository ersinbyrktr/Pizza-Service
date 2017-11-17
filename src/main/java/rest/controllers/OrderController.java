package rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rest.pojos.Pizza;
import rest.repos.OrderRepository;
import rest.repos.PizzaRepository;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController    // This means that this class is a Controller
@RequestMapping(path="/order") // This means URL's start with /demo (after Application path)
public class OrderController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RequestMapping(value="/", method = POST) // Map ONLY GET Requests
    public @ResponseBody
    String addNewPizza (@RequestParam String name,
                        @RequestParam String size,
                        @RequestParam Double price) {
        return "Saved";
    }
}
