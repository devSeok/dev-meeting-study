package study.devmeetingstudy.common.exception.global.error.exception;

public class NotExistStudyLeaderException extends BusinessException {
    public NotExistStudyLeaderException(String message) {
        super(message, ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
