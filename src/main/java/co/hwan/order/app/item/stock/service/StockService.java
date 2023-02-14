package co.hwan.order.app.item.stock.service;

import co.hwan.order.app.common.annotations.DistributeLock;
import co.hwan.order.app.common.exception.InvalidParamException;
import co.hwan.order.app.item.domain.Item;
import co.hwan.order.app.item.stock.web.StockDto;
import co.hwan.order.app.item.stock.web.StockDto.StockRegisterResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockService {

    private static final String STOCK_KEY_PREFIX = "STOCK_";
    private final StringRedisTemplate stringRedisTemplate;

    @Transactional
    public StockRegisterResponse registStockOfItem(StockDto.StockRegisterRequest dto)  {
        int remain = 0;

        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        String res = stringValueOperations.get(dto.getItemToken());

        if(res == null) {
            //신규
            remain = dto.getQuantity();
            stringValueOperations.set(dto.getItemToken(), String.valueOf(remain));
        } else {
            remain = dto.getQuantity() + Integer.parseInt(res);
            stringValueOperations.set(dto.getItemToken(), String.valueOf(remain));
        }

        return StockRegisterResponse.of(
            dto.getItemToken(),
            remain
        );
    }

    @DistributeLock(key = STOCK_KEY_PREFIX)
    public void decreaseStockOfItem(Item item, Integer quantity) {
        String itemToken = Objects.requireNonNull(item.getItemToken());

        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        String res = stringValueOperations.get(item.getItemToken());
        log.info("stock cnt: {}" + res);
        if(res == null) {
            throw new InvalidParamException("등록된 재고가 없습니다.");
        }

        int i = Integer.parseInt(res) - quantity;
        log.info("stock remain: {}" + res);
        stringValueOperations.set(itemToken, String.valueOf(i));
    }
}
