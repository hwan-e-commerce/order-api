package co.hwan.order.common.exception;

import co.hwan.order.common.response.ErrorCode;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException() {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String msg) {
        super(msg, ErrorCode.COMMON_ENTITY_NOT_FOUND);
    }
}
