package rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rest.pojos.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    String FIND_ORDERITEM_IDS = "select distinct t.id from OrderList t";

    @Query(value = FIND_ORDERITEM_IDS, nativeQuery = true)
    List<Integer> findOrderIds();

    OrderItem findById(@Param("id") Integer id);
}
