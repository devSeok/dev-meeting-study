package study.devmeetingstudy.domain.study;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.study.enums.StudyAuth;
import study.devmeetingstudy.domain.study.enums.StudyMemberStatus;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@DynamicInsert
@Getter
public class StudyMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'MEMBER'")  // 최초 방을 생성하는 사람이 리더가 된다.
    private StudyAuth studyAuth;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'JOIN'")
    private StudyMemberStatus studyMemberStatus;

    @Builder
    public StudyMember(Long id, Member member, Study study, StudyAuth studyAuth, StudyMemberStatus studyMemberStatus) {
        this.id = id;
        this.member = member;
        this.study = study;
        this.studyAuth = studyAuth;
        this.studyMemberStatus = studyMemberStatus;
    }

    public static StudyMember create(Member member, Study study) {
        return StudyMember.builder()
                .member(member)
                .study(study)
                .build();
    }

    public static StudyMember createAuthLeader(Member member, Study study){
        return StudyMember.builder()
                .member(member)
                .study(study)
                .studyAuth(StudyAuth.LEADER)
                .build();
    }

    public static void changeStudyMemberStatus(StudyMember studyMember, StudyMemberStatus studyMemberStatus) {
        studyMember.studyMemberStatus = studyMemberStatus;
    }

    public boolean isStudyAuthLeader() {
        return this.studyAuth == StudyAuth.LEADER;
    }
}
