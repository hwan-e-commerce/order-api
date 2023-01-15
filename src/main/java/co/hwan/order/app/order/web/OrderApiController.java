package co.hwan.order.app.order.web;

import co.hwan.order.app.order.service.OrderService;
import co.hwan.order.app.order.web.OrderDto.OrderRegisterDto;
import co.hwan.order.app.order.web.OrderDto.OrderRegisterResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderRegisterResponse> registOrder(@RequestBody OrderRegisterDto registerDto) {
        return ResponseEntity.created(URI.create("/api/v1/orders")).body(orderService.registerOrder(registerDto));
    }
}
