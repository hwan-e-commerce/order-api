package co.hwan.order.app.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."), // 장애 상황
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS("잘못된 상태값입니다."),
    INVALID_STOCK_COUNT("재고수량이 부족합니다."),
    ORDER_QUANTITY_EXCEEDED("주문 수량이 초과되었습니다."),
    STOCK_NOT_SALE("판매 중인 상품이 아닙니다."),
    INVALID_ITEM_PARTNER_ID("Item에 등록된 Partner Id가 없습니다.");

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
