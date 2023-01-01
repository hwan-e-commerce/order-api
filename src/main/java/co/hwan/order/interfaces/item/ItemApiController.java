package co.hwan.order.interfaces.item;

import co.hwan.order.application.item.ItemFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ItemApiController {
    private final ItemFacade itemFacade;
}
