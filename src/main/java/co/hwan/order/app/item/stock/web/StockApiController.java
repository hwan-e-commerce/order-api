package co.hwan.order.app.item.stock.web;

import co.hwan.order.app.item.stock.service.StockService;
import co.hwan.order.app.item.stock.web.StockDto.StockRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
@RestController
public class StockApiController {

    private final StockService service;

    @PostMapping("")
    public ResponseEntity<StockRegisterResponse> registerStock(
        @RequestBody StockDto.StockRegisterRequest dto
    ) {
        return ResponseEntity.ok(service.registStockOfItem(dto));
    }
}
