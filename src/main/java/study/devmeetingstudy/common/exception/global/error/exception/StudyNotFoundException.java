package study.devmeetingstudy.common.exception.global.error.exception;

import study.devmeetingstudy.common.exception.global.error.exception.BusinessException;

public class StudyNotFoundException extends BusinessException {
    public StudyNotFoundException(String message) {
        super(message, ErrorCode.STUDY_NOT_FOUND);
    }
}
