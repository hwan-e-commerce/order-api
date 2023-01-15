package co.hwan.order.app.common.exception;

import co.hwan.order.app.common.response.ErrorCode;

public class InvalidOrderCalculateException extends BaseException {

    public InvalidOrderCalculateException() {
        super(ErrorCode.ORDER_ITEMS_NULL);
    }
}
