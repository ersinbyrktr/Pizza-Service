package rest.projections;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LimitedOrderItem {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("pizzaId")
    private Integer pizzaId;

    @JsonProperty("quantity")
    private Integer quantity;

    // for Jackson
    protected LimitedOrderItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(Integer pizzaId) {
        this.pizzaId = pizzaId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
