package co.hwan.order.app.order.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class OrderItemInfo {
    private final String orderToken;
    private final String itemToken;
    private final Integer orderCnt;
    private final String orderedAt;

    public static OrderItemInfo of(
        String orderToken,
        String orderItemToken,
        Integer orderCnt,
        String orderedAt
    ) {
        return new OrderItemInfo(orderToken , orderItemToken, orderCnt, orderedAt);
    }
}
