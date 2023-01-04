package co.hwan.order.interfaces.item;

import co.hwan.order.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<?> registerItem(@RequestBody ItemDto.RegisterItemRequest request) {
        itemService.registerItem(request);
        return ResponseEntity.ok().build();
    }
}
