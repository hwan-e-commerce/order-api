package co.hwan.order.domain.item;

public interface ItemService {
    void registerItem(ItemCommand.RegisterItemRequest request, String partnerToken);
    void changeOnSale(String itemToken);
    void changeEndOfSale(String itemToken);
}
