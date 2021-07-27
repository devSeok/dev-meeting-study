package study.devmeetingstudy.common.exception.global.error;

import lombok.Getter;
import study.devmeetingstudy.common.exception.global.ErrorCode;

@Getter
public class SignupDuplicateException extends RuntimeException{

    private ErrorCode errorCode;

    public SignupDuplicateException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public SignupDuplicateException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
