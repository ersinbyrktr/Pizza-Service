package rest.pojos;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class Pizza {
    @Id
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


    public Pizza(Integer id, String name, String size, Double price) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public Pizza(Integer id, String name, String size, Double price, Set<Topping> toppings) {
        this.id = id;
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

    public void setToppings(Set<Topping> toppings) {
        this.toppings = toppings;
    }

    public void addTopping(Topping topping) {
        this.toppings.add(topping);
    }
    
    
}

