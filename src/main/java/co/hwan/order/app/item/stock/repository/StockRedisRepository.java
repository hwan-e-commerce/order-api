package co.hwan.order.app.item.stock.repository;

import co.hwan.order.app.item.stock.domain.RedisStock;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface StockRedisRepository extends CrudRepository<RedisStock, Long> {
    Optional<RedisStock> findRedisStockByItemToken(String itemToken);
}
