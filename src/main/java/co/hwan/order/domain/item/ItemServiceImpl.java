package co.hwan.order.domain.item;

import co.hwan.order.domain.item.ItemCommand.RegisterItemRequest;
import co.hwan.order.domain.partner.Partner;
import co.hwan.order.domain.partner.PartnerService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService{

    private final ItemStore itemStore;
    private final PartnerService partnerService;

    @Transactional
    @Override
    public void registerItem(RegisterItemRequest request, String partnerToken) {
        Partner savedPartner = partnerService.findPartnerByToken(partnerToken);
        Item initItem = request.toEntity(savedPartner.getId());
        itemStore.store(initItem);
    }

    @Override
    public void changeOnSale(String itemToken) {

    }

    @Override
    public void changeEndOfSale(String itemToken) {

    }
}
