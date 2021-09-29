package study.devmeetingstudy.dto.study;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyVO {

    private StudySaveReqDto studySaveReqDto;
    private Subject subject;

    public static StudyVO of(StudySaveReqDto studySaveReqDto, Subject subject){
        return new StudyVO(studySaveReqDto, subject);
    }
}
