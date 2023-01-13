package co.hwan.order.app.common.exception;

import co.hwan.order.app.common.response.ErrorCode;

public class InvalidItemStatusException extends BaseException {
    public InvalidItemStatusException() {
        super(ErrorCode.STOCK_NOT_SALE);
    }

}
