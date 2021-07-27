package study.devmeetingstudy.common.exception.global.error.exception;

public class UserException extends BusinessException{
    public UserException(String message) {
        super(message, ErrorCode.USER_NOT_FOUND);
    }
}
