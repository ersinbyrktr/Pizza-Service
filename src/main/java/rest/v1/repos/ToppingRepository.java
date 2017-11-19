package rest.v1.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rest.v1.pojos.Topping;

import java.util.List;


public interface ToppingRepository extends JpaRepository<Topping, Integer> {
    String FIND_TOPPING_IDS = "select distinct t.id from topping t, pizza_topping pt where t.id=pt.topping_id and pt.pizza_id=?1";

    @Query(value = FIND_TOPPING_IDS, nativeQuery = true)
    List<Integer> findToppingIds(@Param("pizzaId") Integer id);

    Topping findById(@Param("id") Integer id);

}
