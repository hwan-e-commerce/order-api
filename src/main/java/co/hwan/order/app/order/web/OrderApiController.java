package co.hwan.order.app.order.web;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/order")
@RestController
public class OrderApiController {

    @PostMapping
    public ResponseEntity<Void> registOrder() {
        return ResponseEntity.created(URI.create("/api/v1/order")).build();
    }
}
