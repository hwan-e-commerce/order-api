package co.hwan.order.app.order.service.dto;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
@RequiredArgsConstructor
public class StockSQSMessage {
    private final List<OrderItemInfo> orderInfos;
}
