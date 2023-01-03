package co.hwan.order.interfaces.item;

import co.hwan.order.application.item.ItemFacade;
import co.hwan.order.domain.item.ItemCommand;
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
    private final ItemFacade itemFacade;

    @PostMapping("")
    public ResponseEntity<?> registerItem(@RequestBody ItemDto.RegisterItemRequest request) {
        ItemCommand.RegisterItemRequest itemCommand = request.toCommand();
        itemFacade.registerItem(itemCommand, request.getPartnerToken());
        return ResponseEntity.ok().build();
    }
}
