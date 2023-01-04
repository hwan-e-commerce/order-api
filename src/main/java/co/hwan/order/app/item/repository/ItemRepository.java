package co.hwan.order.app.item.repository;

import co.hwan.order.app.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
