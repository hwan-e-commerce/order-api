package co.hwan.order.app.item.repository;

import co.hwan.order.app.item.domain.Item;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByItemToken(String itemToken);
}
