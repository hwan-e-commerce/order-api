package co.hwan.order.app.stock.domain;


import co.hwan.order.app.common.exception.InvalidStockException;
import co.hwan.order.app.common.response.ErrorCode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@RedisHash(value = "stock")
public class RedisStock {

    @Id
    private Long id;
    @Indexed
    private final String itemToken;
    private int remain;
    private String createdAt;
    private String updatedAt;

    private RedisStock(String itemToken, int remain) {
        this.itemToken = itemToken;
        this.remain = remain;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static RedisStock of(String itemToken, int quantity) {
        return new RedisStock(itemToken, quantity);
    }

    public void decrease(final int orderQuantity) {
        checkStock(orderQuantity); this.remain -= orderQuantity;
    }

    public void increase(final int value) {
        this.remain += value;
        updatedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private void checkStock(final int orderQuantity) {
        if(remain < orderQuantity) {
            throw new InvalidStockException(ErrorCode.ORDER_QUANTITY_EXCEEDED);
        }

        if(remain < 1) {
            throw new InvalidStockException();
        }
    }
}
