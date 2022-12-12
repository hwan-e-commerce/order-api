package co.hwan.order.orderapi.interfaces.healthcheck;

import co.hwan.order.orderapi.interfaces.dto.HelltoDto;
import co.hwan.order.orderapi.interfaces.dto.response.HealthCheckApiFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/health-check")
@RestController
public class HealthCheckApi {

    @GetMapping
    ResponseEntity<HealthCheckApiFormat> healthCheck() {
        return ResponseEntity.ok(HealthCheckApiFormat.of("OK"));
    }

    @PatchMapping
    public String hello(@RequestBody HelltoDto helltoDto) {
        System.out.println(helltoDto);
        return "hello";
    }

    @PutMapping
    public String helloPut(@RequestBody HelltoDto helltoDto) {
        System.out.println(helltoDto);
        return "hello";
    }
}
