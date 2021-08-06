package study.devmeetingstudy.common.exception.global.error.exception;

public class UserOutException extends BusinessException{
    public UserOutException(String message) {
        super(message, ErrorCode.USER_OUT);
    }
}
