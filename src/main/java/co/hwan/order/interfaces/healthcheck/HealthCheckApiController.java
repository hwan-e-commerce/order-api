package co.hwan.order.interfaces.healthcheck;


import co.hwan.order.interfaces.healthcheck.dto.response.HealthCheckApiFormat;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/health-check")
@RestController
public class HealthCheckApiController {

    @GetMapping
    ResponseEntity<HealthCheckApiFormat> healthCheck() {
        log.info("current time: {}", LocalDateTime.now());
        return ResponseEntity.ok(HealthCheckApiFormat.of("OK V4"));
    }
}
