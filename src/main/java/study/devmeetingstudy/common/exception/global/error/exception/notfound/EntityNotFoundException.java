package study.devmeetingstudy.common.exception.global.error.exception.notfound;

import study.devmeetingstudy.common.exception.global.error.exception.BusinessException;
import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
