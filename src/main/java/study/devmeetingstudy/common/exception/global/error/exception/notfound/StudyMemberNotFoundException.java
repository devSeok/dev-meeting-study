package study.devmeetingstudy.common.exception.global.error.exception.notfound;

import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;

public class StudyMemberNotFoundException extends ResourceNotFoundException {

    public StudyMemberNotFoundException(String message) {
        super(message, ErrorCode.MESSAGE_NOT_FOUND);
    }
}
