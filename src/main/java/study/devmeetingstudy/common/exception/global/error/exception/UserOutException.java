package study.devmeetingstudy.common.exception.global.error.exception;

public class UserOutException extends BusinessException{

    public UserOutException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
