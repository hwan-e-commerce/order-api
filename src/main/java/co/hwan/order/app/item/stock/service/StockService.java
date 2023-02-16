package co.hwan.order.app.item.stock.service;

import co.hwan.order.app.common.annotations.DistributeLock;
import co.hwan.order.app.common.exception.InvalidParamException;
import co.hwan.order.app.item.domain.Item;
import co.hwan.order.app.item.stock.web.StockDto;
import co.hwan.order.app.item.stock.web.StockDto.StockRegisterResponse;
import co.hwan.order.app.order.service.StockSQSMessageSender;
import co.hwan.order.app.order.service.dto.OrderItemInfo;
import co.hwan.order.app.order.service.dto.StockSQSMessage;
import co.hwan.order.app.order.service.dto.StockSQSMessage.Type;
import java.util.List;
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
    private final StockSQSMessageSender sqsMessageSender;

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
        checkStockExistInRedis(res);

        int remain = Integer.parseInt(res);
        checkStockIsZeroInRedis(remain);
        remain = remain - quantity;
        checkOrderQuantityIsValid(remain);
        stringValueOperations.set(itemToken, String.valueOf(remain));
    }

    public void sendStockMessageFromOrderItems(List<OrderItemInfo> orderItemInfos, Type type) {
        StockSQSMessage stockSQSMessage = new StockSQSMessage(orderItemInfos, type);
        sqsMessageSender.sendStockMessage(stockSQSMessage);
    }


    private void checkStockExistInRedis(String stockOfRedis) {
        if(stockOfRedis == null) {
            throw new InvalidParamException("등록된 재고가 없습니다.");
        }
    }

    private void checkStockIsZeroInRedis(int stock) {
        if(stock == 0) {
            throw new InvalidParamException("재고가 모두 소진되었습니다.");
        }
    }

    private void checkOrderQuantityIsValid(int remain) {
        if(remain < 0) {
            throw new InvalidParamException("재고가 부족합니다.");
        }
    }


}
