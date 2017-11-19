package rest.v1.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import rest.v1.pojos.Pizza;

import java.util.List;


public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    String FIND_PIZZA_IDS = "select distinct t.id from Pizza t";

    @Query(value = FIND_PIZZA_IDS, nativeQuery = true)
    List<Integer> findPizzaIds();

    Pizza findById(@Param("id") Integer id);

}
