package co.hwan.order.app.common.exception;

import co.hwan.order.app.common.response.ErrorCode;

public class InvalidStockException extends BaseException {

    public InvalidStockException() {
        super(ErrorCode.INVALID_STOCK_COUNT);
    }

    public InvalidStockException(ErrorCode errorCode) {
        super(errorCode);
    }
}
