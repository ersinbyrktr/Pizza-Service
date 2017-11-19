package rest.v1.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Integer id;

    @JoinTable(name = "orderorderitems")
    @OneToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JsonProperty("OrderItems")
    private Set<OrderItem> orderItems;

    @Column
    @JsonProperty("totalPrice")
    private Double totalPrice;

    @Column
    @JsonProperty("recipient")
    private String recipient;

    // for Jackson
    protected Order() {
    }

    public Order(Set<OrderItem> orderItems, String recipient) {
        this.orderItems = orderItems;
        this.recipient = recipient;
        updatePrice();
    }

    private Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Map<String, Object>> getOrderItems() {
        Set<Map<String, Object>> projectiledOrderItems = new HashSet<>();
        for (OrderItem orderItem:orderItems) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("pizzaId", orderItem.getPizzaId());
            tmp.put("quantity", orderItem.getQuantity());
            projectiledOrderItems.add(tmp);
        }
        return projectiledOrderItems;
    }

    @JsonIgnore
    public Set<OrderItem> getAllOrderItems() {
        return orderItems;
    }

    @JsonIgnore
    public Location getLocation(String baseUrl){
        return new Location(baseUrl+"/"+getId());
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    private void updatePrice(){
        totalPrice = 0D;
        for (OrderItem orderItem:orderItems) {
            totalPrice += orderItem.getPizza().getPrice()*orderItem.getQuantity();
        }
        totalPrice =  Math.round(totalPrice * 100D) / 100D;
    }
}
