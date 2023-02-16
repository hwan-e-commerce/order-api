package co.hwan.order.app.order.service;

import co.hwan.order.app.common.exception.EntityNotFoundException;
import co.hwan.order.app.item.domain.Item;
import co.hwan.order.app.item.repository.ItemRepository;
import co.hwan.order.app.item.stock.service.StockService;
import co.hwan.order.app.order.domain.Order;
import co.hwan.order.app.order.domain.OrderItem;
import co.hwan.order.app.order.domain.OrderItemOption;
import co.hwan.order.app.order.domain.OrderItemOptionGroup;
import co.hwan.order.app.order.repository.OrderRepository;
import co.hwan.order.app.order.service.dto.OrderItemInfo;
import co.hwan.order.app.order.service.dto.StockSQSMessage.Type;
import co.hwan.order.app.order.web.OrderDto;
import co.hwan.order.app.order.web.OrderDto.OrderRegisterResponse;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final StockService stockService;

    @Transactional
    public OrderRegisterResponse registerOrder(OrderDto.OrderRegisterDto orderRegisterDto) {
        Order initOrder = orderRegisterDto.toEntity(orderRegisterDto.toFragment());

        List<OrderItem> orderItems = orderRegisterDto.getOrderItemList()
            .stream()
            .map(
                registerOrderItem -> {
                    Item item = itemRepository.findWithPessimisticLockByItemToken(registerOrderItem.getItemToken())
                        .orElseThrow(EntityNotFoundException::new);

                    stockService.decreaseStockOfItem(item, registerOrderItem.getOrderCount());

                    OrderItem orderItem = registerOrderItem.toEntity(initOrder, item.getId(), item.getPartnerId());

                    List<OrderItemOptionGroup> orderItemOptionGroups = registerOrderItem
                        .getOrderItemOptionGroupList()
                        .stream()
                        .map(registerOrderItemOptionGroupRequest -> {
                            OrderItemOptionGroup orderItemOptionGroup = registerOrderItemOptionGroupRequest.toEntity(orderItem);

                            List<OrderItemOption> orderItemOptions = registerOrderItemOptionGroupRequest.getOrderItemOptionList()
                                .stream()
                                .map(registerOrderItemOptionRequest -> registerOrderItemOptionRequest.toEntity(orderItemOptionGroup))
                                .collect(Collectors.toList());

                            orderItemOptionGroup.setOrderItemOptions(orderItemOptions);
                            return orderItemOptionGroup;
                        })
                        .collect(Collectors.toList());

                    orderItem.setOrderItemOptionGroups(orderItemOptionGroups);
                    return orderItem;
                }
            ).collect(Collectors.toList());

        initOrder.setOrderItems(orderItems);
        initOrder.calculateTotalAmount();
        Order savedOrder = orderRepository.save(initOrder);

        // 주문 성공 후 요청을 보냄
        List<OrderItemInfo> orderItemInfos = savedOrder.getOrderItems()
            .stream()
            .map(
                orderItem -> OrderItemInfo.of(
                    initOrder.getOrderToken(),
                    orderItem.getItemToken(),
                    orderItem.getOrderCount(),
                    initOrder.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE)
            ))
            .collect(Collectors.toList());

        stockService.sendStockMessageFromOrderItems(orderItemInfos, Type.DECREASE);

        return OrderRegisterResponse
            .builder()
            .orderToken(savedOrder.getOrderToken())
            .orderNumber(savedOrder.getOrderNumber())
            .orderStatus(savedOrder.getStatus().getDescription())
            .totalPrice(savedOrder.getTotalPrice())
            .deliveryAddress(savedOrder.getDeliveryFragment().createFullAddress())
            .receiverName(savedOrder.getDeliveryFragment().getReceiverName())
            .build();
    }

}
