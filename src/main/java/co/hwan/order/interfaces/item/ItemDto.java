package co.hwan.order.interfaces.item;

import co.hwan.order.domain.item.Item;
import co.hwan.order.domain.item.ItemCommand;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ItemDto {

    @Getter
    @Setter
    @ToString
    public static class RegisterItemRequest {
        private String partnerToken;
        private String itemName;
        private Long itemPrice;
        private List<RegisterItemOptionGroupRequest> itemOptionGroupList;


        public ItemCommand.RegisterItemRequest toCommand() {
            List<ItemCommand.RegisterItemOptionGroupRequest> optionGroupRequests = new ArrayList<>();

            for(RegisterItemOptionGroupRequest registerItemOptionGroupRequest : itemOptionGroupList) {
                optionGroupRequests.add(registerItemOptionGroupRequest.toRegisterItemOptionGroupRequest());
            }

            return ItemCommand.RegisterItemRequest.builder()
                .itemName(itemName)
                .itemPrice(itemPrice)
                .itemOptionGroupRequestList(optionGroupRequests)
                .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterItemOptionGroupRequest {
        private Integer ordering;
        private String itemOptionGroupName;
        private List<RegisterItemOptionRequest> itemOptionList;

        public ItemCommand.RegisterItemOptionGroupRequest toRegisterItemOptionGroupRequest() {
            List<ItemCommand.RegisterItemOptionRequest> optionRequests = new ArrayList<>();

            for(RegisterItemOptionRequest registerItemOptionRequest : itemOptionList) {
                optionRequests.add(registerItemOptionRequest.toRegisterItemOption());
            }

            return ItemCommand.RegisterItemOptionGroupRequest.builder()
                .ordering(ordering)
                .itemOptionGroupName(itemOptionGroupName)
                .itemOptionRequestList(optionRequests)
                .build();
        }
    }

    @Getter
    @Setter
    public static class RegisterItemOptionRequest {
        private Integer ordering;
        private String itemOptionName;
        private Long itemOptionPrice;

        public ItemCommand.RegisterItemOptionRequest toRegisterItemOption() {
            return ItemCommand.RegisterItemOptionRequest.builder()
                .ordering(ordering)
                .itemOptionName(itemOptionName)
                .itemOptionPrice(itemOptionPrice)
                .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterResponse {
        private final String itemToken;
    }

    @Getter
    @Setter
    @ToString
    public static class ChangeStatusItemRequest {
        private String itemToken;
    }

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final String itemToken;
        private final Long partnerId;
        private final String itemName;
        private final Long itemPrice;
        private final Item.Status status;
        private final List<ItemOptionGroupInfo> itemOptionGroupList;
    }

    @Getter
    @Builder
    @ToString
    public static class ItemOptionGroupInfo {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<ItemOptionInfo> itemOptionList;
    }

    @Getter
    @Builder
    @ToString
    public static class ItemOptionInfo {
        private final Integer ordering;
        private final String itemOptionName;
        private final Long itemOptionPrice;
    }
}