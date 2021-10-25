package study.devmeetingstudy.common.exception.global.error.exception;

import study.devmeetingstudy.common.exception.global.error.exception.BusinessException;
import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;

public class UnableApplyStudyMemberException extends BusinessException {

    public UnableApplyStudyMemberException(String message) {
        super(message, ErrorCode.UNABLE_APPLY_STUDYMEMBER);
    }
}
