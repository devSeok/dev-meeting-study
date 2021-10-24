package study.devmeetingstudy.common.exception.global.error.exception.notfound;

import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;

public class StudyMemberNotFoundException extends EntityNotFoundException {

    public StudyMemberNotFoundException(String message) {
        super(message, ErrorCode.STUDY_MEMBER_NOT_FOUND);
    }
}
