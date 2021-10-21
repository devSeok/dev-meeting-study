package study.devmeetingstudy.common.exception.global.error.exception.notfound;

import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;
import study.devmeetingstudy.common.exception.global.error.exception.notfound.ResourceNotFoundException;

public class SubjectNotFoundException extends ResourceNotFoundException {

    public SubjectNotFoundException(String message) {
        super(message, ErrorCode.SUBJECT_NOT_FOUND);
    }
}
