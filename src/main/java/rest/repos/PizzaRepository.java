package rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import rest.pojos.Pizza;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    public static final String FIND_PIZZA_IDS = "select distinct t.id from Pizza t";

    @Query(value = FIND_PIZZA_IDS, nativeQuery = true)
    List<Integer> findPizzaIds();

    public Pizza findById(@Param("id") Integer id);

}
