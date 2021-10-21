package study.devmeetingstudy.common.exception.global.error.exception.notfound;

import study.devmeetingstudy.common.exception.global.error.exception.ErrorCode;

public class StudyFileNotFoundException extends ResourceNotFoundException {

    public StudyFileNotFoundException(String message) {
        super(message, ErrorCode.STUDY_FILE_NOT_FOUND);
    }
}
