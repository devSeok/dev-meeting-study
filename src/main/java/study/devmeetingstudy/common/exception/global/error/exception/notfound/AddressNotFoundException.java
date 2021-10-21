package study.devmeetingstudy.common.exception.global.error.exception.notfound;

import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;

public class AddressNotFoundException extends ResourceNotFoundException {


    public AddressNotFoundException(String message) {
        super(message, ErrorCode.ADDRESS_NOT_FOUND);
    }
}
