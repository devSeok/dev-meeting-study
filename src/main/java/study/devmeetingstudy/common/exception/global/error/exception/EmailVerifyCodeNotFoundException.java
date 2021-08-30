package study.devmeetingstudy.common.exception.global.error.exception;

public class EmailVerifyCodeNotFoundException extends BusinessException{


    public EmailVerifyCodeNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public EmailVerifyCodeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
