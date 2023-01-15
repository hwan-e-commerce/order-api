package co.hwan.order.app.order.repository;

import co.hwan.order.app.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
