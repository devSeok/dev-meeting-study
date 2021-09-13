package study.devmeetingstudy.common.exception.global.error.exception;

public class AddressNotFoundException extends BusinessException {


    public AddressNotFoundException(String message) {
        super(message, ErrorCode.ADDRESS_NOT_FOUND);
    }
}
