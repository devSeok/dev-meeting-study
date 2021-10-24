package study.devmeetingstudy.domain.study;


import lombok.*;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.study.request.StudyPutReqDto;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Online {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "online_id")
    private Long id;

    private String onlineType;

    private String link;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Builder
    public Online(Long id, String onlineType, String link, Study study) {
        this.onlineType = onlineType;
        this.link = link;
        this.study = study;
        this.id = id;
    }

    public static Online create(StudySaveReqDto dto, Study study){
        return Online.builder()
                .onlineType(dto.getOnlineType())
                .link(dto.getLink())
                .study(study)
                .build();
    }

    public static Online replace(StudyPutReqDto studyPutReqDto, Online foundOnline) {
        foundOnline.onlineType = studyPutReqDto.getOnlineType();
        foundOnline.link = studyPutReqDto.getLink();
        return foundOnline;
    }
}
