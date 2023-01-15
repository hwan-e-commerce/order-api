package co.hwan.order.app.order.service;

import co.hwan.order.app.common.exception.EntityNotFoundException;
import co.hwan.order.app.item.domain.Item;
import co.hwan.order.app.item.repository.ItemRepository;
import co.hwan.order.app.order.domain.Order;
import co.hwan.order.app.order.domain.OrderItem;
import co.hwan.order.app.order.domain.OrderItemOption;
import co.hwan.order.app.order.domain.OrderItemOptionGroup;
import co.hwan.order.app.order.repository.OrderRepository;
import co.hwan.order.app.order.web.OrderDto;
import co.hwan.order.app.order.web.OrderDto.OrderRegisterResponse;
import co.hwan.order.app.stock.service.StockRedisService;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final StockRedisService stockRedisService;

    @Transactional
    public OrderRegisterResponse registerOrder(OrderDto.OrderRegisterDto orderRegisterDto) {
        log.info("orderRegisterDto {}", orderRegisterDto);
        Order initOrder = orderRegisterDto.toEntity(orderRegisterDto.toFragment());

        List<OrderItem> orderItems = orderRegisterDto.getOrderItemList().stream()
            .map(
                registerOrderItem -> {
                    log.info("item token: {}", registerOrderItem.getItemToken());
                    Item item = itemRepository.findByItemToken(registerOrderItem.getItemToken())
                        .orElseThrow(EntityNotFoundException::new);

                    OrderItem orderItem = registerOrderItem.toEntity(initOrder, item.getId(), item.getPartnerId());

                    List<OrderItemOptionGroup> orderItemOptionGroups = registerOrderItem.getOrderItemOptionGroupList()
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
            )
            .collect(Collectors.toList());

        initOrder.setOrderItems(orderItems);
        initOrder.calculateTotalAmount();
        Order savedOrder = orderRepository.save(initOrder);

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
