package co.hwan.order.app.stock.web;

import co.hwan.order.app.stock.service.StockRedisService;
import co.hwan.order.app.stock.web.StockDto.StockRegisterResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/stock")
@RestController
public class StockApiController {

    private final StockRedisService stockService;

    public StockApiController(StockRedisService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("")
    public ResponseEntity<StockRegisterResponse> registerStock(
        @RequestBody StockDto.StockRegisterRequest request
    ) {
        StockRegisterResponse res = stockService.registerStock(request);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/{itemToken}")
    public ResponseEntity<?> getStock(@PathVariable String itemToken) {
        StockRegisterResponse res = stockService.getStockByItemToken(itemToken);
        return ResponseEntity.ok().body(res);
    }
}
