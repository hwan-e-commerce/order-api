package co.hwan.order.app.order.domain;

import co.hwan.order.app.common.abstractentity.Timestamp;
import co.hwan.order.app.common.exception.InvalidParamException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor
@Entity
@Table(name="order_items")
public class OrderItem extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    private Long partnerId;
    private Long itemId;
    private String itemName;
    private String itemToken;
    private Long itemPrice;
    private Integer orderCount;
    private DeliveryStatus deliveryStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItem", cascade = CascadeType.PERSIST )
    private List<OrderItemOptionGroup> orderItemOptionGroups = new ArrayList<>();

    @Getter
    @AllArgsConstructor
    public enum DeliveryStatus {
        BEFORE_DELIVERY("배송전"),
        DELIVERY_PREPARE("배송준비중"),
        DELIVERING("배송중"),
        COMPLETE_DELIVERY("배송완료");
        private final String description;
    }

    @Builder
    public OrderItem(
        Order order,
        Integer orderCount,
        Long partnerId,
        Long itemId,
        String itemName,
        String itemToken,
        Long itemPrice
    ) {
        if (order == null) throw new InvalidParamException("OrderItemLine.order");
        if (orderCount == null) throw new InvalidParamException("OrderItemLine.orderCount");
        if (partnerId == null) throw new InvalidParamException("OrderItemLine.partnerId");
        if (itemId == null && StringUtils.isEmpty(itemName))
            throw new InvalidParamException("OrderItemLine.itemNo and itemName");
        if (StringUtils.isEmpty(itemToken)) throw new InvalidParamException("OrderItemLine.itemToken");
        if (itemPrice == null) throw new InvalidParamException("OrderItemLine.itemPrice");

        this.order = order;
        this.orderCount = orderCount;
        this.partnerId = partnerId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemToken = itemToken;
        this.itemPrice = itemPrice;
        this.deliveryStatus = DeliveryStatus.BEFORE_DELIVERY;
    }

    public void setOrderItemOptionGroups(List<OrderItemOptionGroup> orderItemOptionGroups) {
        this.orderItemOptionGroups = orderItemOptionGroups;
    }

    public Long calculateTotalAmount() {
        Long itemOptionTotalAmount = this.orderItemOptionGroups.stream()
            .mapToLong(OrderItemOptionGroup::calculateTotalAmount)
            .sum();
        return this.itemPrice + itemOptionTotalAmount;
    }

}
