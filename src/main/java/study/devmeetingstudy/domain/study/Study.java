package study.devmeetingstudy.domain.study;

import lombok.*;
import lombok.experimental.SuperBuilder;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.base.BaseTimeEntity;
import study.devmeetingstudy.domain.study.enums.StudyType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Study extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String title;
    private int maxMember;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private StudyType studyType;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyFile> files = new ArrayList<>();

    public Study(Subject subject, String title, int maxMember, LocalDateTime startDate, LocalDateTime endDate, StudyType studyType) {
        this.subject = subject;
        this.title = title;
        this.maxMember = maxMember;
        this.startDate = startDate;
        this.endDate = endDate;
        this.studyType = studyType;
    }
}
