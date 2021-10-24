package study.devmeetingstudy.common.exception.global.error.exception.notfound;

import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;

public class AddressNotFoundException extends EntityNotFoundException {


    public AddressNotFoundException(String message) {
        super(message, ErrorCode.ADDRESS_NOT_FOUND);
    }
}
