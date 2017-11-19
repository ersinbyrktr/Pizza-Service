package rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rest.v1.pojos.*;
import rest.v1.repos.OrderItemRepository;
import rest.v1.repos.OrderRepository;
import rest.v1.repos.PizzaRepository;
import rest.v1.repos.ToppingRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Application  implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    public Application(PizzaRepository pizzaRepository, ToppingRepository toppingRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.pizzaRepository = pizzaRepository;
        this.toppingRepository = toppingRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final PizzaRepository pizzaRepository;
    private final ToppingRepository toppingRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        // Example Data
        Topping Topping1 = new Topping( "Topping1", 2.45);
        Topping Topping2 = new Topping( "Topping2", 3.75);
        Topping Topping3 = new Topping( "Topping3", 1.20);
        Topping Topping4 = new Topping( "Topping4", 21.45);
        Topping Topping5 = new Topping(  "Topping5", 7.45);
        Topping Topping6 = new Topping(  "Topping6", 3.45);
        Set<Topping> toppings1 = new HashSet<Topping>(){{
            add(Topping1);
            add(Topping2);
            add(Topping3);
        }};
        Set<Topping> toppings2 = new HashSet<Topping>(){{
            add(Topping4);
            add(Topping5);
            add(Topping6);
        }};
        toppingRepository.save(toppings1);
        toppingRepository.save(toppings2);
        Pizza Pizza1 = new Pizza( "Pizza1", Size.Standard, toppings1);
        Pizza Pizza2 = new Pizza( "Pizza2", Size.Large, toppings2);
        pizzaRepository.save(new HashSet<Pizza>() {{
            add(Pizza1);
            add(Pizza2);
        }});
        OrderItem OrderItem1 = new OrderItem(Pizza1, 2);
        OrderItem OrderItem2 = new OrderItem(Pizza2, 3);
        OrderItem OrderItem3 = new OrderItem(Pizza1, 4);
        orderItemRepository.save(OrderItem1);
        orderItemRepository.save(OrderItem2);
        orderItemRepository.save(OrderItem3);
        Set<OrderItem> OIS1 = new HashSet<OrderItem>() {{
            add(OrderItem1);
            add(OrderItem2);
        }};
        Set<OrderItem> OIS2 = new HashSet<OrderItem>() {{
            add(OrderItem1);
            add(OrderItem2);
            add(OrderItem3);
        }};
        Order order1 = new Order(OIS1, "asdas");
        Order order2 = new Order(OIS2, "qweasdas");
        orderRepository.save(order1);

    }
}
