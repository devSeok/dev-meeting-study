package study.devmeetingstudy.common.exception.global.error.exception;

import study.devmeetingstudy.common.exception.global.error.exception.BusinessException;
import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;

public class ExistsStudyMemberException extends BusinessException {

    public ExistsStudyMemberException(String message) {
        super(message, ErrorCode.EXISTS_STUDY_MEMBER);
    }
}
