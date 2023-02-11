package co.hwan.order.app.item.stock.repository;

import co.hwan.order.app.item.stock.domain.Stock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByItemToken(String itemToken);

}
