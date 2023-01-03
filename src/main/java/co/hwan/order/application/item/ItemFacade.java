package co.hwan.order.application.item;

import co.hwan.order.domain.item.ItemCommand;
import co.hwan.order.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemFacade {

    private final ItemService itemService;

    public void registerItem(ItemCommand.RegisterItemRequest registerItemRequest, String partnerToken) {
        itemService.registerItem(registerItemRequest, partnerToken);
    }
}
