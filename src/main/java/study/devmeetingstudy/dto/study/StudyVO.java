package study.devmeetingstudy.dto.study;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.Offline;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyVO {

    private StudySaveReqDto studySaveReqDto;
    private Subject subject;
    private Online online;
    private Offline offline;
    private Study study;

    public StudyVO(StudySaveReqDto studySaveReqDto, Subject subject) {
        this.studySaveReqDto = studySaveReqDto;
        this.subject = subject;
    }

    public StudyVO(Study study, Online online) {
        this.study = study;
        this.online = online;
    }

    public StudyVO(Study study, Offline offline) {
        this.study = study;
        this.offline = offline;
    }

    public static StudyVO of(StudySaveReqDto studySaveReqDto, Subject subject) {
        return new StudyVO(studySaveReqDto, subject);
    }

    public static StudyVO of(Study study, Online online) {
        return new StudyVO(study, online);
    }

    public static StudyVO of(Study study, Offline offline) {
        return new StudyVO(study, offline);
    }
}
