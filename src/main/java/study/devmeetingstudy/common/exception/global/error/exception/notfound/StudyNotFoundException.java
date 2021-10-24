package study.devmeetingstudy.common.exception.global.error.exception.notfound;

import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;

public class StudyNotFoundException extends EntityNotFoundException {
    public StudyNotFoundException(String message) {
        super(message, ErrorCode.STUDY_NOT_FOUND);
    }
}
