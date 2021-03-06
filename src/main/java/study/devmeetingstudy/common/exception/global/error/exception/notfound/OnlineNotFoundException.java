package study.devmeetingstudy.common.exception.global.error.exception.notfound;

import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;

public class OnlineNotFoundException extends EntityNotFoundException {

    public OnlineNotFoundException(String message) {
        super(message, ErrorCode.ONLINE_NOT_FOUND);
    }
}
