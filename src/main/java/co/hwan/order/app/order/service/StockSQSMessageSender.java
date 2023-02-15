package co.hwan.order.app.order.service;

import co.hwan.order.app.order.service.dto.StockSQSMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.SqsMessageHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StockSQSMessageSender {

    private final String SQS_QUEUE_NAME = "stock-event-sqs.fifo";
    private final QueueMessagingTemplate queueMessagingTemplate;

    public void sendStockMessage(StockSQSMessage message) {
        try {
            Map<String, Object> headers = new HashMap<>();
            headers.put(SqsMessageHeaders.SQS_GROUP_ID_HEADER, "stock-event-queue");
            headers.put(SqsMessageHeaders.SQS_DEDUPLICATION_ID_HEADER, UUID.randomUUID().toString());
            queueMessagingTemplate.convertAndSend(SQS_QUEUE_NAME, message, headers);
            log.info("[SQS enqueued topic: {}, message: {}", SQS_QUEUE_NAME, message);
        } catch (Exception e) {
            log.error("SQS failed topic: {}, message: {}", SQS_QUEUE_NAME, message);
            throw new RuntimeException(e);
        }
    }
}
