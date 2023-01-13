package co.hwan.order.app.stock.service;

import co.hwan.order.app.common.exception.EntityNotFoundException;
import co.hwan.order.app.common.exception.InvalidParamException;
import co.hwan.order.app.item.domain.Item;
import co.hwan.order.app.item.repository.ItemRepository;
import co.hwan.order.app.stock.domain.RedisStock;
import co.hwan.order.app.stock.repository.StockRedisRepository;
import co.hwan.order.app.stock.web.StockDto;
import co.hwan.order.app.stock.web.StockDto.StockRegisterRequest;
import co.hwan.order.app.stock.web.StockDto.StockRegisterResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockRedisService {

    private final StockRedisRepository stockRepository;
    private final ItemRepository itemRepository;

    public StockRedisService(StockRedisRepository stockRepository, ItemRepository itemRepository) {
        this.stockRepository = stockRepository; this.itemRepository = itemRepository;
    }

    public StockRegisterResponse registerStock(StockRegisterRequest request) {
        Item item = itemRepository.findByItemToken(request.getItemToken())
            .orElseThrow(EntityNotFoundException::new);

        item.checkOnSale();

        RedisStock stock = RedisStock.of(item.getItemToken(), request.getQuantity());
        RedisStock saved = stockRepository.save(stock);

        return StockDto.StockRegisterResponse.of(
            saved.getId(),
            saved.getItemToken(),
            saved.getRemain(),
            saved.getCreatedAt()
        );
    }

    public StockRegisterResponse getStockByItemToken(String itemToken) {
        return stockRepository.findRedisStockByItemToken(itemToken)
            .map(stock -> StockRegisterResponse.of(stock.getId(), stock.getItemToken(), stock.getRemain(), stock.getCreatedAt()))
            .orElseThrow(InvalidParamException::new);
    }
}
