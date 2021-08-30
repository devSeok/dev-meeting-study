package study.devmeetingstudy.common.exception.global.error.exception;

public class MessageNotFoundException extends BusinessException{

    public MessageNotFoundException(String errorCode) {
        super(errorCode, ErrorCode.MESSAGE_NOT_FOUND);
    }
}
