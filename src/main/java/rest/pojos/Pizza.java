package rest.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column(length = 32, columnDefinition = "varchar(255)")
    @Enumerated(value = EnumType.STRING)
    private Size size;

    @Column
	private Double price;

    @OneToMany
    private Set<Topping> toppings;

    // for Jackson
    protected Pizza() {
    }


    public Pizza(String name, Size size, Double price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public Pizza(String name, Size size, Double price, Set<Topping> toppings) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.toppings = toppings;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

    public Double getPrice() {
        return price;
    }

    @JsonIgnore
	public Double getTotalPrice() {
        Double toppingPrice= 0.;
        for (Topping topping:toppings) {
            toppingPrice += topping.getPrice();
        }
        return price + toppingPrice;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


    @JsonIgnore
    public Set<Topping> getToppings() {
        return toppings;
    }

    @JsonIgnore
    public Set<Integer> getToppingIds() {
        Set<Integer> ids = new HashSet<>();
        for (Topping topping: toppings) {
            ids.add(topping.getId());
        }
        return ids;
    }

    @JsonIgnore
    public Topping getToppingById(Integer toppingId) {
        for (Topping topping: toppings) {
            if (Objects.equals(topping.getId(), toppingId)){
                return topping;
            }
        }
        return null;
    }

    public boolean deleteToppingById(Integer toppingId) {
        for (Topping topping: toppings) {
            if (topping.getId().equals(toppingId)){
                toppings.remove(topping);
                return true;
            }
        }
        return false;
    }

    public void setToppings(Set<Topping> toppings) {
        this.toppings = toppings;
    }

    @JsonIgnore
    public Location getLocation(String baseUrl){
        return new Location(baseUrl+getId());
    }
}

