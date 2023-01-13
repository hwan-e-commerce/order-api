package co.hwan.order.app.item.web;

import co.hwan.order.app.item.domain.Item;
import co.hwan.order.app.item.itemoption.domain.ItemOption;
import co.hwan.order.app.item.itemoptiongroup.domain.ItemOptionGroup;
import co.hwan.order.app.partner.domain.Partner;
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
    public static class ItemRegisterResponse {
        private final String partnerName;
        private final String itemName;
        private final String itemToken;

        private ItemRegisterResponse(String partnerName, String itemName, String itemToken) {
            this.partnerName = partnerName;
            this.itemName = itemName;
            this.itemToken = itemToken;
        }

        public static ItemRegisterResponse of(String partnerName, String itemName, String itemToken) {
            return new ItemRegisterResponse(partnerName, itemName, itemToken);
        }
    }

    @Getter
    public static class ChangeStatusItemRequest {
        private String itemToken;
    }

    @Getter
    public static class ItemDetailResponse {
        private final String itemToken;
        private final String partnerName;
        private final String contractStatus;
        private final String itemName;
        private final Long  itemPrice;
        private List<ItemOptionGroupResponse> optionGroups;

        private ItemDetailResponse(String itemToken, String partnerName, String contractStatus, String itemName,
            Long itemPrice, List<ItemOptionGroupResponse> optionGroups) {
            this.itemToken = itemToken;
            this.partnerName = partnerName;
            this.contractStatus = contractStatus;
            this.itemName = itemName;
            this.itemPrice = itemPrice;
            this.optionGroups = optionGroups;
        }

        public static ItemDetailResponse of(String itemToken, String partnerName, Partner.Status status, String itemName, Long itemPrice, List<ItemOptionGroupResponse> optionGroups) {
            return new ItemDetailResponse(itemToken, partnerName, status.getDescription(), itemName, itemPrice, optionGroups);
        }
    }

    @Getter
    public static class ItemOptionGroupResponse {
        private final String itemOptionGroupName;
        private List<ItemOptionResponse> options;

        private ItemOptionGroupResponse(String itemOptionGroupName, List<ItemOptionResponse> options) {
            this.itemOptionGroupName = itemOptionGroupName;
            this.options = options;
        }

        public static ItemOptionGroupResponse of(String itemOptionGroupName, List<ItemOptionResponse> options) {
            return new ItemOptionGroupResponse(itemOptionGroupName, options);
        }
    }

    @Getter
    public static class ItemOptionResponse {
        private final String optionName;
        private final Long itemOptionPrice;

        private ItemOptionResponse(String optionName, Long itemOptionPrice) {
            this.optionName = optionName;
            this.itemOptionPrice = itemOptionPrice;
        }

        public static ItemOptionResponse of(String optionName, Long itemOptionPrice) {
            return new ItemOptionResponse(optionName, itemOptionPrice);
        }
    }
}