package co.hwan.order.app.item.stock.domain;

import co.hwan.order.app.common.abstractentity.Timestamp;
import co.hwan.order.app.common.exception.InvalidParamException;
import co.hwan.order.app.common.exception.InvalidStockException;
import co.hwan.order.app.common.response.ErrorCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@NoArgsConstructor
@Entity
@Table(name = "stocks")
public class Stock extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String itemToken;

    @Column(nullable = false)
    private Integer remain;

    public Stock(String itemToken, Integer remain) {
        this.itemToken = itemToken;
        this.remain = remain;
    }

    public void changeRemain(Integer quantity) {
        this.remain = quantity;
    }

    public void decrease(Integer quantity) {
        checkValidRemain(quantity);
        this.remain -= quantity;
    }

    /**
     * 반품처리
     */
    public void increase(Integer quantity) {
        this.remain += quantity;
    }

    private void checkValidRemain(Integer quantity) {
        if(this.remain < 1) throw new InvalidStockException();
        if(this.remain - quantity < 0) throw new InvalidStockException(ErrorCode.ORDER_QUANTITY_EXCEEDED);
        if(quantity < 1) {
            log.info("Order Item Quantity: {}L", quantity);
            throw new InvalidParamException(ErrorCode.INVALID_QUANTITY);
        }
    }
}
