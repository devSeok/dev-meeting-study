package study.devmeetingstudy.common.exception.global.error.exception;

public class StudyFileNotFoundException extends BusinessException{


    public StudyFileNotFoundException(String message) {
        super(message, ErrorCode.STUDY_FILE_NOT_FOUND);
    }
}
