package rest.v1.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "orderitem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Integer id;

    @Transient
    private Pizza pizza;

    @Column
    private Integer pizzaId;

    @Column
    private Integer quantity;

    // for Jackson
    protected OrderItem() {
    }

    public OrderItem(Pizza pizza, Integer quantity) {
        setPizza(pizza);
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        if (pizza == null){
            this.pizza = null;
            return;
        }
        this.pizza = pizza;
        this.pizzaId = pizza.getId();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPizzaId(){
        return pizzaId;
    }


    public void setPizzaId(Integer pizzaId) {
        this.pizzaId = pizzaId;
    }
}
