package study.devmeetingstudy.common.exception.global.error.exception;

public class MessageNotFoundException extends BusinessException{

    public MessageNotFoundException(String message) {
        super(message, ErrorCode.MESSAGE_NOT_FOUND);
    }
}
