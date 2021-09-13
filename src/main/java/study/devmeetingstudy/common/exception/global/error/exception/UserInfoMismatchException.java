package study.devmeetingstudy.common.exception.global.error.exception;

public class UserInfoMismatchException extends BusinessException{

    public UserInfoMismatchException(String message) {
        super(message, ErrorCode.USER_INFO_MISMATCH);
    }
}
