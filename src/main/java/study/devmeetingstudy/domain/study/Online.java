package study.devmeetingstudy.domain.study;


import lombok.*;
import lombok.experimental.SuperBuilder;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.study.request.StudyStoreRequestDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("Online")
public class Online extends Study{

    private String onlineType;

    private String link;

    @Builder
    public Online(Subject subject, String title, int maxMember,
                  LocalDateTime statDate, LocalDateTime endDate,
                  StudyType studyType, String onlineType, String link) {

        super(subject, title, maxMember, statDate, endDate, studyType);
        this.onlineType = onlineType;
        this.link = link;
    }

    public static Online createOnline(StudyStoreRequestDto dto, Optional<Subject> subject){
        return Online.builder()
                .subject(subject.get())
                .title(dto.getTitle())
                .maxMember(dto.getMaxMember())
                .statDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .studyType(StudyType.FREE)
                .onlineType("tes")
                .link("sd")
                .build();
    }
}
