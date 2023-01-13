package co.hwan.order.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class OrderApiApplication {
    @Value("${spring.datasource.url}")
    static private String jdbcHost;
//    @Value("${spring.redis.host}")
//    static private String redisHost;

    public static void main(String[] args) {
        SpringApplication.run(OrderApiApplication.class, args);
        log.info("==================");
        log.info("jdbc url: {}", jdbcHost);
    }
}
