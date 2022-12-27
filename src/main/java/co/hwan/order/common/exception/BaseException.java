package co.hwan.order.common.exception;

import co.hwan.order.common.response.ErrorCode;

public class BaseException extends RuntimeException {
    private ErrorCode errorCode;

    public BaseException() {}

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getErrorMsg());
        this.errorCode = errorCode;
    }

    public BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
