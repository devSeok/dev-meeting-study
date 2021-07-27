package study.devmeetingstudy.common.exception.global.error.exception;

public class SignupDuplicateException extends BusinessException{

    public SignupDuplicateException(String errorCode) {
        super(errorCode, ErrorCode.EMAIL_DUPLICATION);
    }
}
