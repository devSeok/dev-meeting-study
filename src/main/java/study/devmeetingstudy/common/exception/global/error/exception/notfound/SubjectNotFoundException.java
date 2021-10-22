package study.devmeetingstudy.common.exception.global.error.exception.notfound;

import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;

public class SubjectNotFoundException extends EntityNotFoundException {

    public SubjectNotFoundException(String message) {
        super(message, ErrorCode.SUBJECT_NOT_FOUND);
    }
}
