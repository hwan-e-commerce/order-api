package co.hwan.order.app.order.web;

import co.hwan.order.app.common.exception.InvalidParamException;
import co.hwan.order.app.order.domain.Order;
import co.hwan.order.app.order.domain.OrderItem;
import co.hwan.order.app.order.domain.OrderItemOption;
import co.hwan.order.app.order.domain.OrderItemOptionGroup;
import co.hwan.order.app.order.fragment.DeliveryFragment;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

public class OrderDto {

    @Getter
    public static class OrderRegisterDto {
        @NotBlank(message = "payMethod 는 필수값입니다")
        private String payMethod;

        @NotBlank(message = "receiverName 는 필수값입니다")
        private String receiverName;

        @NotBlank(message = "receiverPhone 는 필수값입니다")
        private String receiverPhone;

        @NotBlank(message = "receiverZipcode 는 필수값입니다")
        private String receiverZipcode;

        @NotBlank(message = "receiverAddress1 는 필수값입니다")
        private String receiverAddress1;

        @NotBlank(message = "receiverAddress2 는 필수값입니다")
        private String receiverAddress2;

        @NotBlank(message = "etcMessage 는 필수값입니다")
        private String etcMessage;

        private List<RegisterOrderItem> orderItemList;

        public DeliveryFragment toFragment() {
            return DeliveryFragment.builder()
                .receiverName(receiverName)
                .receiverPhone(receiverPhone)
                .receiverZipcode(receiverZipcode)
                .receiverAddress1(receiverAddress1)
                .receiverAddress2(receiverAddress2)
                .etcMessage(etcMessage)
                .build();
        }

        public Order toEntity(DeliveryFragment deliveryFragment) {
            return Order.builder()
                .payMethod(payMethod)
                .deliveryFragment(deliveryFragment)
                .build();
        }
    }

    @Getter
    public static class RegisterOrderItem {
        @NotNull(message = "orderCount 는 필수값입니다")
        private Integer orderCount;

        @NotBlank(message = "itemToken 는 필수값입니다")
        private String itemToken;

        @NotBlank(message = "itemName 는 필수값입니다")
        private String itemName;

        @NotNull(message = "itemPrice 는 필수값입니다")
        private Long itemPrice;

        private List<RegisterOrderItemOptionGroupRequest> orderItemOptionGroupList;

        public OrderItem toEntity(Order order, Long itemId, Long partnerId) {
            return OrderItem.builder()
                .order(order)
                .orderCount(orderCount)
                .itemId(itemId)
                .partnerId(partnerId)
                .itemToken(itemToken)
                .itemName(itemName)
                .itemPrice(itemPrice)
                .build();
        }

    }

    @Getter
    public static class RegisterOrderItemOptionGroupRequest {
        @NotNull(message = "ordering 는 필수값입니다")
        private Integer ordering;

        @NotBlank(message = "itemOptionGroupName 는 필수값입니다")
        private String itemOptionGroupName;

        private List<RegisterOrderItemOptionRequest> orderItemOptionList;

        public OrderItemOptionGroup toEntity(OrderItem orderItem) {
            if(orderItem == null) throw new InvalidParamException("order item is null");
            return OrderItemOptionGroup.builder()
                .ordering(ordering)
                .orderItem(orderItem)
                .itemOptionGroupName(itemOptionGroupName)
                .build();
        }
    }

    @Getter
    public static class RegisterOrderItemOptionRequest {
        private Integer ordering;

        @NotBlank(message = "itemOptionName 는 필수값입니다")
        private String itemOptionName;

        @NotNull(message = "itemOptionPrice 는 필수값입니다")
        private Long itemOptionPrice;

        public OrderItemOption toEntity(OrderItemOptionGroup orderItemOptionGroup) {
            return OrderItemOption.builder()
                .orderItemOptionGroup(orderItemOptionGroup)
                .ordering(ordering)
                .itemOptionName(itemOptionName)
                .itemOptionPrice(itemOptionPrice)
                .build();
        }
    }

    @Getter
    public static class OrderRegisterResponse {
        private final String orderToken;
        private final String orderNumber;
        private final String orderStatus;
        private final Long totalPrice;
        private final String deliveryAddress;
        private final String receiverName;

        @Builder
        public OrderRegisterResponse(String orderToken, String orderNumber, String orderStatus,
            Long totalPrice, String deliveryAddress, String receiverName) {
            this.orderToken = orderToken;
            this.orderNumber = orderNumber;
            this.orderStatus = orderStatus;
            this.totalPrice = totalPrice;
            this.deliveryAddress = deliveryAddress;
            this.receiverName = receiverName;
        }
    }
}
