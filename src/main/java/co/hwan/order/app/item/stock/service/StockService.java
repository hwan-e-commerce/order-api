package co.hwan.order.app.item.stock.service;

import co.hwan.order.app.common.annotations.DistributeLock;
import co.hwan.order.app.common.exception.EntityNotFoundException;
import co.hwan.order.app.common.exception.InvalidParamException;
import co.hwan.order.app.item.domain.Item;
import co.hwan.order.app.item.repository.ItemRepository;
import co.hwan.order.app.item.stock.domain.Stock;
import co.hwan.order.app.item.stock.repository.StockRepository;
import co.hwan.order.app.item.stock.web.StockDto;
import co.hwan.order.app.item.stock.web.StockDto.StockRegisterResponse;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockService {

    private static final String STOCK_KEY_PREFIX = "STOCK_";
    private final ItemRepository itemRepository;
    private final StockRepository stockRepository;

    @Transactional
    public StockRegisterResponse registStockOfItem(StockDto.StockRegisterRequest dto)  {
        itemRepository.findByItemToken(dto.getItemToken())
            .orElseThrow(() -> new InvalidParamException("Item Token is Not Found"));

        Stock stock = dto.toEntity();
        stockRepository.save(stock);

        return StockRegisterResponse.of(
            stock.getItemToken(),
            stock.getRemain(),
            stock.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE)
        );
    }

    @DistributeLock(key = STOCK_KEY_PREFIX)
    public void decreaseStockOfItem(Item item, Integer quantity) {
        String itemToken = Objects.requireNonNull(item.getItemToken());
        Stock stock = stockRepository.findByItemToken(itemToken)
            .orElseThrow(EntityNotFoundException::new);

        stock.decrease(quantity);
    }
}
