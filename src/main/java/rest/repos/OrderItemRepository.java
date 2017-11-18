package rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rest.pojos.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    String FIND_ORDERITEMs_BY_PIZZA_ID = "select * from order_item t where t.pizza_id=?1";


    @Query(value = FIND_ORDERITEMs_BY_PIZZA_ID, nativeQuery = true)
    List<OrderItem> findOrderItemsByPizzaId(@Param("pizzaId") Integer pizzaId);

}
