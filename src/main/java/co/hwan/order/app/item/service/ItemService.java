package co.hwan.order.app.item.service;

import co.hwan.order.app.common.exception.EntityNotFoundException;
import co.hwan.order.app.common.exception.ItemPartnerIdNotValidException;
import co.hwan.order.app.item.domain.Item;
import co.hwan.order.app.item.itemoption.domain.ItemOption;
import co.hwan.order.app.item.itemoptiongroup.domain.ItemOptionGroup;
import co.hwan.order.app.item.repository.ItemRepository;
import co.hwan.order.app.item.web.ItemDto.ItemDetailResponse;
import co.hwan.order.app.item.web.ItemDto.ItemOptionGroupResponse;
import co.hwan.order.app.item.web.ItemDto.ItemOptionResponse;
import co.hwan.order.app.item.web.ItemDto.ItemRegisterResponse;
import co.hwan.order.app.item.web.ItemDto.RegisterItemRequest;
import co.hwan.order.app.partner.domain.Partner;
import co.hwan.order.app.partner.repository.PartnerRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final PartnerRepository partnerRepository;

    public ItemService(ItemRepository itemRepository, PartnerRepository partnerRepository) {
        this.itemRepository = itemRepository;
        this.partnerRepository = partnerRepository;
    }

    @Transactional
    public ItemRegisterResponse registerItem(RegisterItemRequest registerItemRequest) {
        String partnerToken = registerItemRequest.getPartnerToken();
        Partner partner = partnerRepository.findByPartnerToken(partnerToken)
            .orElseThrow(() -> new EntityNotFoundException("Partner Not Exist"));

        Item initItem = registerItemRequest.toEntity(partner.getId());
        Item storedItem = itemRepository.save(initItem);

        List<ItemOptionGroup> itemOptionGroups = registerItemRequest.getItemOptionGroupList()
            .stream()
            .map(itemOptionGroupRequest -> {
                ItemOptionGroup itemOptionGroup = itemOptionGroupRequest.toEntity(storedItem);
                List<ItemOption> itemOptions = itemOptionGroupRequest.getItemOptionList().stream()
                    .map(itemOptionRequest -> itemOptionRequest.toEntity(itemOptionGroup))
                    .collect(Collectors.toList());

                itemOptionGroup.addOptions(itemOptions);

                return itemOptionGroup;
            }).collect(Collectors.toList());

        storedItem.addItemOptionGroup(itemOptionGroups);

        return ItemRegisterResponse.of(partner.getPartnerName(), storedItem.getItemName(), storedItem.getItemToken());
    }

    @Transactional
    public ItemDetailResponse getItemDetailByToken(String itemToken) {
        Item item = itemRepository.findByItemToken(itemToken)
            .orElseThrow(EntityNotFoundException::new);

        Long partnerId = item.getPartnerId();
        Partner partner = partnerRepository.findById(partnerId)
            .orElseThrow(() -> new ItemPartnerIdNotValidException("Item에 등록된 Parnter Id가 존재하지 않습니다."));

        List<ItemOptionGroupResponse> itemOptionGroupResponses = item.getItemOptionGroupList().stream()
            .map(
                itemOptionGroup -> {
                    List<ItemOptionResponse> options = itemOptionGroup.getItemOptionList().stream().map(
                        itemOption -> ItemOptionResponse.of(itemOption.getItemOptionName(),
                            item.getItemPrice())).collect(Collectors.toList());
                    return ItemOptionGroupResponse.of(itemOptionGroup.getItemOptionGroupName(), options);
                }
            )
            .collect(Collectors.toList());

        return ItemDetailResponse.of(
            item.getItemToken(),
            partner.getPartnerName(),
            partner.getStatus(),
            item.getItemName(),
            item.getItemPrice(),
            itemOptionGroupResponses
        );
    }

}
