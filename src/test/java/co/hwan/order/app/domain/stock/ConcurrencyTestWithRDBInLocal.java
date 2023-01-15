package co.hwan.order.app.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;

import co.hwan.order.app.stock.domain.RedisStock;
import co.hwan.order.app.stock.repository.StockRedisRepository;
import co.hwan.order.app.stock.service.StockRedisService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConcurrencyTestWithRDBInLocal {

    static final String ITEM_TOKEN = "itm_SbPtEyamKbbfBBbj";

    @Autowired
    private StockRedisService stockService;
    @Autowired
    private StockRedisRepository stockRepository;


    /**
     *  시나리오
     *  1. DB에 저장된 Item을 조회 후
     *  2. 수량 감소
     *  3. item 수량이 0보다 작다면 Throw
     */
    @DisplayName("동시성 테스트")
    @Test
    void test() throws InterruptedException {
        // given
        final int ORDER_COUNT = 50;

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(ORDER_COUNT);

        for(int i = 0; i < ORDER_COUNT; i++) {
            executorService.submit(
                () -> {
                    try {
                        stockService.decreaseStock(ITEM_TOKEN, 1);
                    } finally {
                        latch.countDown();
                    }
                }
            );
        }

        latch.await();

        // then
        RedisStock savedStock = stockRepository.findRedisStockByItemToken(ITEM_TOKEN).get();
        System.out.println("cnt: " + savedStock.getRemain());
        assertThat(savedStock.getRemain()).isEqualTo(50);
    }
}
