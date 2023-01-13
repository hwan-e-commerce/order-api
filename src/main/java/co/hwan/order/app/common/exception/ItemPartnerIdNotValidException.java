package co.hwan.order.app.common.exception;

import co.hwan.order.app.common.response.ErrorCode;

public class ItemPartnerIdNotValidException extends BaseException {

    public ItemPartnerIdNotValidException(String message) {
        super(message, ErrorCode.INVALID_ITEM_PARTNER_ID);
    }
}
