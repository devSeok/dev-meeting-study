package study.devmeetingstudy.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.Offline;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StudySaveVO {

    private StudySaveReqDto studySaveReqDto;
    private Subject subject;
    private Online online;
    private Offline offline;
    private Study study;

    public StudySaveVO(StudySaveReqDto studySaveReqDto, Subject subject) {
        this.studySaveReqDto = studySaveReqDto;
        this.subject = subject;
    }

    public StudySaveVO(Study study, Online online) {
        this.study = study;
        this.online = online;
    }

    public StudySaveVO(Study study, Offline offline) {
        this.study = study;
        this.offline = offline;
    }

    public static StudySaveVO of(StudySaveReqDto studySaveReqDto, Subject subject) {
        return new StudySaveVO(studySaveReqDto, subject);
    }

    public static StudySaveVO of(Study study, Online online) {
        return new StudySaveVO(study, online);
    }

    public static StudySaveVO of(Study study, Offline offline) {
        return new StudySaveVO(study, offline);
    }
}
