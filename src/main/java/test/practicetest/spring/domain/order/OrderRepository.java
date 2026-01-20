package test.practicetest.spring.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByRegisteredDateTimeBetweenAndOrderStatus(LocalDateTime createDateTimeAfter, LocalDateTime createDateTimeBefore, OrderStatus orderStatus);

}
