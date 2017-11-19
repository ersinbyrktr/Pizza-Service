package rest.v1.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import rest.v1.pojos.Order;
import rest.v1.pojos.OrderItem;
import rest.v1.pojos.Pizza;
import rest.v1.projections.LimitedOrderItem;
import rest.v1.repos.OrderItemRepository;
import rest.v1.repos.OrderRepository;
import rest.v1.repos.PizzaRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path="v1/order")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PizzaRepository pizzaRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderItemRepository orderItemRepository, PizzaRepository pizzaRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.pizzaRepository = pizzaRepository;
    }

    @RequestMapping(method = POST) // Map ONLY GET Requests
    public @ResponseBody
    ResponseEntity addNewOrder (@RequestParam String recipient,
                                          @RequestParam(name="orderItems") String rawOrderItems,
                                          HttpServletRequest request)  {
        Set<LimitedOrderItem> orderItems = new HashSet<>();
        try {
            orderItems = new ObjectMapper().readValue(rawOrderItems, new TypeReference<Set<LimitedOrderItem>>() {});
        } catch (IOException e) {
            return resolveBadRequestException();
        }
        Set<OrderItem> newOrderItems = new HashSet<>();
        for (LimitedOrderItem tmp:orderItems) {
            Pizza tmpPizza = pizzaRepository.findById(tmp.getPizzaId());
            if(tmpPizza == null || tmp.getQuantity() == null) return resolveBadRequestException();
            OrderItem tmpOrderItem = new OrderItem(tmpPizza, tmp.getQuantity());
            newOrderItems.add(tmpOrderItem);
        }
        orderItemRepository.save(newOrderItems);
        Order current_order = new Order(newOrderItems, recipient);
        orderRepository.save(current_order);
        return new ResponseEntity<>(current_order.getLocation(request.getRequestURL().toString()), HttpStatus.CREATED);
    }


    @RequestMapping( method= GET)
    public @ResponseBody
    ResponseEntity<List<Integer>> getAllOrders() {
        List<Integer> result = orderRepository.findOrderIds();
        if(result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }


    @RequestMapping(value = "{orderId:[0-9A-Za-z]+}", method = GET)
    public @ResponseBody ResponseEntity<Order> getOrderById(@PathVariable("orderId")  Integer orderId) {
        Order current_order = orderRepository.findById(orderId);
        return new ResponseEntity<>(current_order, HttpStatus.OK);
    }


    @RequestMapping(value = "{orderId:[0-9A-Za-z]+}", method = DELETE)
    public @ResponseBody
    ResponseEntity<Object> deleteOrderById(@PathVariable("orderId")  Integer orderId) {
        try {
            Order currentOrder = orderRepository.findById(orderId);
            if (currentOrder == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Set<OrderItem> tmpOrderItems = currentOrder.getAllOrderItems();
            currentOrder.setOrderItems(new HashSet<>());
            orderRepository.save(currentOrder);
            orderItemRepository.delete(tmpOrderItems);
            orderRepository.delete(orderId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    //Exception Handling

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity resolveBadRequestException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
