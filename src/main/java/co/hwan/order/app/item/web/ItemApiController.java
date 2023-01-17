package co.hwan.order.app.item.web;

import co.hwan.order.app.item.service.ItemService;
import co.hwan.order.app.item.stock.web.StockDto.StockRegisterRequest;
import co.hwan.order.app.item.stock.web.StockDto.StockRegisterResponse;
import co.hwan.order.app.item.web.ItemDto.ItemDetailResponse;
import co.hwan.order.app.item.web.ItemDto.ItemRegisterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
@RestController
public class ItemApiController {

    private final ItemService itemService;

    @PostMapping("")
    public ResponseEntity<ItemRegisterResponse> registerItem(@RequestBody ItemDto.RegisterItemRequest request) {
        return ResponseEntity.ok().body(itemService.registerItem(request));
    }

    @GetMapping("/{itemToken}")
    public ResponseEntity<ItemDetailResponse> getItemDetailByToken(@PathVariable String itemToken) {
        ItemDetailResponse itemDetailResponse = itemService.getItemDetailByToken(itemToken);
        return ResponseEntity.ok().body(itemDetailResponse);
    }

    @PutMapping("/stocks")
    public ResponseEntity<StockRegisterResponse> registerItemStock(
        @RequestBody StockRegisterRequest stockRegisterRequest
    ) {
        return ResponseEntity.ok().body(itemService.registerItemStock(stockRegisterRequest));
    }
}
