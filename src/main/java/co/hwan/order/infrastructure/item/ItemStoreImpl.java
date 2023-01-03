package co.hwan.order.infrastructure.item;

import co.hwan.order.common.exception.InvalidParamException;
import co.hwan.order.domain.item.Item;
import co.hwan.order.domain.item.ItemStore;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ItemStoreImpl implements ItemStore {
    private final ItemRepository itemRepository;

    @Override
    public void store(Item item) {
        validCheck(item);
        itemRepository.save(item);
    }

    private void validCheck(Item item) {
        if (StringUtils.isEmpty(item.getItemToken())) throw new InvalidParamException("Item.itemToken");
        if (StringUtils.isEmpty(item.getItemName())) throw new InvalidParamException("Item.itemName");
        if (item.getPartnerId() == null) throw new InvalidParamException("Item.partnerId");
        if (item.getItemPrice() == null) throw new InvalidParamException("Item.itemPrice");
        if (item.getStatus() == null) throw new InvalidParamException("Item.status");
    }
}
