package co.hwan.order.orderapi.interfaces.healthcheck;

import co.hwan.order.orderapi.interfaces.dto.response.HealthCheckApiFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/health-check")
@RestController
public class HealthCheckApi {

    @GetMapping
    ResponseEntity<HealthCheckApiFormat> healthCheck() {
        return ResponseEntity.ok(HealthCheckApiFormat.of("OK"));
    }
}
