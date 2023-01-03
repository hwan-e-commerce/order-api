package co.hwan.order.infrastructure.item;

import co.hwan.order.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
