package study.devmeetingstudy.domain.study;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.base.BaseTimeEntity;
import study.devmeetingstudy.domain.enums.DeletionStatus;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
//@DiscriminatorColumn
//@Inheritance(strategy = InheritanceType.JOINED)
public class Study extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Enumerated(EnumType.STRING)
    private StudyInstanceType dtype;

    private String title;

    private int maxMember;

    private LocalDate startDate;

    private LocalDate endDate;

    private String content;

    @Enumerated(EnumType.STRING)
    private StudyType studyType;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NOT_DELETED'")
    private DeletionStatus deletionStatus;

    @OneToOne(mappedBy = "study", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Online online;

    @OneToOne(mappedBy = "study", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Offline offline;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private final List<StudyFile> files = new ArrayList<>();

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private final List<StudyMember> studyMembers = new ArrayList<>();

    @Builder
    public Study(Subject subject, String title, int maxMember, LocalDate startDate, LocalDate endDate, StudyType studyType, StudyInstanceType dtype, String content, DeletionStatus deletionStatus) {
        this.subject = subject;
        this.title = title;
        this.maxMember = maxMember;
        this.startDate = startDate;
        this.endDate = endDate;
        this.studyType = studyType;
        this.dtype = dtype;
        this.content = content;
        this.deletionStatus = deletionStatus;
    }

    public static Study create(StudySaveReqDto studySaveReqDto, Subject subject) {
        return Study.builder()
                .subject(subject)
                .title(studySaveReqDto.getTitle())
                .maxMember(studySaveReqDto.getMaxMember())
                .startDate(studySaveReqDto.getStartDate())
                .endDate(studySaveReqDto.getEndDate())
                .studyType(studySaveReqDto.getStudyType())
                .dtype(studySaveReqDto.getDtype())
                .content(studySaveReqDto.getContent())
                .build();
    }

    public static boolean isDtypeOnline(Study study){
        return study.getDtype() == StudyInstanceType.ONLINE;
    }

//    public static boolean isInstanceOnline(Study study){
//        return study instanceof Online;
//    }
}
