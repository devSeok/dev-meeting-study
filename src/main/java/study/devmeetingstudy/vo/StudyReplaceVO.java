package study.devmeetingstudy.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.request.StudyPutReqDto;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyReplaceVO {

    private StudyPutReqDto studyPutReqDto;
    private Subject subject;

    public static StudyReplaceVO of(StudyPutReqDto studyPutReqDto, Subject subject) {
        return new StudyReplaceVO(studyPutReqDto, subject);
    }
}
