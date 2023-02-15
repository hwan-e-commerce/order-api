package co.hwan.order.app.order.service.dto;

import co.hwan.order.app.order.domain.OrderItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class OrderItemInfo {
    private final String itemToken;
    private final Integer orderCnt;
    private final Type type;
    public enum Type {
        INCREASE,
        DECREASE
    }

    public static OrderItemInfo of(OrderItem orderItem, Type type) {
        return new OrderItemInfo(orderItem.getItemToken(), orderItem.getOrderCount(), type);
    }
}
