package co.hwan.order.app.order.domain;

import co.hwan.order.app.common.abstractentity.Timestamp;
import co.hwan.order.app.common.exception.InvalidOrderCalculateException;
import co.hwan.order.app.common.exception.InvalidParamException;
import co.hwan.order.app.common.util.TokenGenerator;
import co.hwan.order.app.order.fragment.DeliveryFragment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order extends Timestamp {

    private static final String ORDER_PREFIX = "ord_";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderToken;
    private String orderNumber;
    private String payMethod;
    private Long totalPrice;
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems;

    @Embedded
    private DeliveryFragment deliveryFragment;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        INIT("주문시작"),
        ORDER_COMPLETE("주문완료"),
        DELIVERY_PREPARE("배송준비"),
        IN_DELIVERY("배송중"),
        DELIVERY_COMPLETE("배송완료");

        private final String description;
    }

    @Builder
    public Order(
        String payMethod,
        DeliveryFragment deliveryFragment
    ) {
        if (StringUtils.isEmpty(payMethod)) throw new InvalidParamException("Order.payMethod");
        if (deliveryFragment == null) throw new InvalidParamException("Order.deliveryFragment");

        this.orderToken = TokenGenerator.randomCharacterWithPrefix(ORDER_PREFIX);
        this.payMethod = payMethod;
        this.orderNumber = createOrderNumber();
        this.deliveryFragment = deliveryFragment;
        this.status = Status.INIT;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void calculateTotalAmount() {
        if(this.orderItems == null) throw new InvalidOrderCalculateException();
        this.totalPrice = this.orderItems.stream()
            .mapToLong(OrderItem::calculateTotalAmount)
            .sum();
    }

    private String createOrderNumber() {
        final String prefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        final String randomString = RandomStringUtils.randomNumeric(10);
        return prefix + randomString;
    }
}
