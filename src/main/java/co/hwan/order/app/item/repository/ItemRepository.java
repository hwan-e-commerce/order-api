package co.hwan.order.app.item.repository;

import co.hwan.order.app.item.domain.Item;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemToken(String itemToken);
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<Item> findWithPessimisticLockByItemToken( String itemToken);
}
