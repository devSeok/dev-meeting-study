package study.devmeetingstudy.common.exception.global.error.exception.notfound;

import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;

public class OfflineNotFoundException extends EntityNotFoundException {

    public OfflineNotFoundException(String message) {
        super(message, ErrorCode.OFFLINE_NOT_FOUND);
    }
}
