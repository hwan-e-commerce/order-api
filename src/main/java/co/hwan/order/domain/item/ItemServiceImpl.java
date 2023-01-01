package co.hwan.order.domain.item;

import co.hwan.order.domain.item.ItemCommand.RegisterItemRequest;
import co.hwan.order.domain.partner.PartnerReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService{

    private final PartnerReader partnerReader;
    private final ItemStore itemStore;
    private final ItemReader itemReader;

    @Override
    public void registerItem(RegisterItemRequest request, String partnerToken) {

    }

    @Override
    public void changeOnSale(String itemToken) {

    }

    @Override
    public void changeEndOfSale(String itemToken) {

    }
}
