package co.hwan.order.app.healthcheck.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public class HealthCheckApiFormat {

    private final String message;
    private final String currentTime;

    private HealthCheckApiFormat(String message, String localDateTime) {
        this.message = message;
        this.currentTime = localDateTime;
    }

    public static HealthCheckApiFormat of(String message) {
        if(ObjectUtils.isEmpty(message)) throw  new IllegalArgumentException("message must not be empty");
        return new HealthCheckApiFormat(message, LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}
