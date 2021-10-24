package study.devmeetingstudy.common.exception.global.error.exception.notfound;

import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;

public class MessageNotFoundException extends EntityNotFoundException {

    public MessageNotFoundException(String message) {
        super(message, ErrorCode.MESSAGE_NOT_FOUND);
    }
}
