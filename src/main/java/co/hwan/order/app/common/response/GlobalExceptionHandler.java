package co.hwan.order.app.common.response;

import co.hwan.order.app.common.exception.InvalidParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.debug(ex.getMessage());
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getFieldError());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParamException.class)
    public ErrorResponse handleInvalidParamException(InvalidParamException ex) {
        log.debug(ex.getMessage());
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
