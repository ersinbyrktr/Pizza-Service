package rest.pojos;

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

    @Column
    private String size;

    @Column
	private Double price;

    @OneToMany
    private Set<Topping> toppings;

    // for Jackson
    protected Pizza() {
    }


    public Pizza(String name, String size, Double price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public Pizza(String name, String size, Double price, Set<Topping> toppings) {
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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


    public Set<Topping> getToppings() {
        return toppings;
    }

    public Set<Integer> getToppingIds() {
        Set<Integer> ids = new HashSet<>();
        for (Topping topping: toppings) {
            ids.add(topping.getId());
        }
        return ids;
    }

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

    public void addTopping(Topping topping) {
        this.toppings.add(topping);
    }
    
    
}

