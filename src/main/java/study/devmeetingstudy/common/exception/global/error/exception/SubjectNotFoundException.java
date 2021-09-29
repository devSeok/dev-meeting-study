package study.devmeetingstudy.common.exception.global.error.exception;

public class SubjectNotFoundException extends BusinessException{

    public SubjectNotFoundException(String message) {
        super(message, ErrorCode.SUBJECT_NOT_FOUND);
    }
}
