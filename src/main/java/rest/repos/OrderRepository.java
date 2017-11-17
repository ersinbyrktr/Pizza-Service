package rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rest.pojos.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    String FIND_ORDER_IDS = "select distinct t.id from Order t";

    @Query(value = FIND_ORDER_IDS, nativeQuery = true)
    List<Integer> findOrderIds();

    Order findById(@Param("id") Integer id);
}
