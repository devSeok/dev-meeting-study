package study.devmeetingstudy.common.exception.global.error.exception;

public class StudyMemberNotFoundException extends BusinessException{

    public StudyMemberNotFoundException(String message) {
        super(message, ErrorCode.MESSAGE_NOT_FOUND);
    }
}
