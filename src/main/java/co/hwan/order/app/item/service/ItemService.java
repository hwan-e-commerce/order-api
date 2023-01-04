package co.hwan.order.app.item.service;

import co.hwan.order.app.common.exception.EntityNotFoundException;
import co.hwan.order.app.item.domain.Item;
import co.hwan.order.app.item.itemoption.domain.ItemOption;
import co.hwan.order.app.item.itemoptiongroup.domain.ItemOptionGroup;
import co.hwan.order.app.partner.domain.Partner;
import co.hwan.order.app.item.repository.ItemRepository;
import co.hwan.order.app.partner.repository.PartnerRepository;
import co.hwan.order.app.item.web.ItemDto;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final PartnerRepository partnerRepository;

    @Transactional
    public void registerItem(ItemDto.RegisterItemRequest registerItemRequest) {
        String partnerToken = registerItemRequest.getPartnerToken();
        Partner partner = partnerRepository.findByPartnerToken(partnerToken)
            .orElseThrow(() -> new EntityNotFoundException("Partner Not Exist"));

        Item initItem = registerItemRequest.toEntity(partner.getId());

        List<ItemOptionGroup> itemOptionGroups = registerItemRequest.getItemOptionGroupList().stream()
            .map(itemOptionGroupRequest -> {
                ItemOptionGroup itemOptionGroup = itemOptionGroupRequest.toEntity(initItem);

//                itemOptionGroupRequest.getItemOptionList().forEach(
//                    itemOptionRequest -> {
//                        ItemOption itemOption = itemOptionRequest.toEntity(itemOptionGroup);
////                        itemOptionRepository.save(itemOption); // 이 방법은 option group id가 null이 되어 SQL Error가 발생함 // 해결법은 한 번 save를 호출해준 뒤 진행하거나 영속성전이를 사용한다면 연관관계 맵핑 후 루트에서 저장
//                    }
//                );
                // todo
                // 위에 그냥 save를 호출 했을 때 FK가 연결되나?
                // 안된다면 list로 반환해서 연관관계 맵핑을 해주자
//                itemOptionGroupRequest.getItemOptionList().

                List<ItemOption> itemOptions = itemOptionGroupRequest.getItemOptionList().stream()
                    .map(itemOptionRequest -> itemOptionRequest.toEntity(itemOptionGroup))
                    .collect(Collectors.toList());

                itemOptionGroup.addOptions(itemOptions);

                return itemOptionGroup;
            }).collect(Collectors.toList());

        initItem.addItemOptionGroup(itemOptionGroups);
        itemRepository.save(initItem);
    }

}
