package co.hwan.order.interfaces.healthcheck;


import co.hwan.order.interfaces.healthcheck.dto.response.HealthCheckApiFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/health-check")
@RestController
public class HealthCheckApiController {

    @GetMapping
    ResponseEntity<HealthCheckApiFormat> healthCheck() {
        return ResponseEntity.ok(HealthCheckApiFormat.of("OK V4"));
    }
}
