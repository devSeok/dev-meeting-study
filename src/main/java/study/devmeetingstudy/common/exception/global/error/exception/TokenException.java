package study.devmeetingstudy.common.exception.global.error.exception;

public class TokenException extends BusinessException{
    public TokenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
