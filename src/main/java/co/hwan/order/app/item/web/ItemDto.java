package co.hwan.order.app.item.web;

import co.hwan.order.app.item.domain.Item;
import co.hwan.order.app.item.itemoption.domain.ItemOption;
import co.hwan.order.app.item.itemoptiongroup.domain.ItemOptionGroup;
import java.util.List;
import lombok.Getter;

public class ItemDto {

    @Getter
    public static class RegisterItemRequest {
        private String partnerToken;
        private String itemName;
        private Long itemPrice;
        private List<RegisterItemOptionGroupRequest> itemOptionGroupList;

        public Item toEntity(Long partnerId) {
            return Item.builder()
                .itemName(itemName)
                .partnerId(partnerId)
                .itemPrice(itemPrice)
                .build();
        }
    }

    @Getter
    public static class RegisterItemOptionGroupRequest {
        private Integer ordering;
        private String itemOptionGroupName;
        private List<RegisterItemOptionRequest> itemOptionList;

        public ItemOptionGroup toEntity(Item item) {
            return ItemOptionGroup.builder()
                .item(item)
                .ordering(ordering)
                .itemOptionGroupName(itemOptionGroupName)
                .build();
        }
    }

    @Getter
    public static class RegisterItemOptionRequest {
        private Integer ordering;
        private String itemOptionName;
        private Long itemOptionPrice;

        public ItemOption toEntity(ItemOptionGroup itemOptionGroup) {
            return ItemOption.builder()
                .itemOptionGroup(itemOptionGroup)
                .ordering(ordering)
                .itemOptionName(itemOptionName)
                .itemOptionPrice(itemOptionPrice)
                .build();
        }
    }


    @Getter
    public static class ChangeStatusItemRequest {
        private String itemToken;
    }
}