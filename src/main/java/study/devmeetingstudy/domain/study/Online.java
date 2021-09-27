package study.devmeetingstudy.domain.study;


import lombok.*;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("Online")
public class Online extends Study{

    private String onlineType;

    private String link;

    @Builder
    public Online(Subject subject, String title, int maxMember,
                  LocalDate statDate, LocalDate endDate,
                  StudyType studyType, String onlineType, String link) {

        super(subject, title, maxMember, statDate, endDate, studyType);
        this.onlineType = onlineType;
        this.link = link;
    }

    public static Online create(StudySaveReqDto dto, Subject subject){
        return Online.builder()
                .subject(subject)
                .title(dto.getTitle())
                .maxMember(dto.getMaxMember())
                .statDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .studyType(dto.getStudyType())
                .onlineType(dto.getOnlineType())
                .link(dto.getLink())
                .build();
    }
}
