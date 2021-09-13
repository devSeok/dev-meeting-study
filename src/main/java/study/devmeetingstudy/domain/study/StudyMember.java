package study.devmeetingstudy.domain.study;


import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.study.enums.StudyAuth;
import study.devmeetingstudy.domain.study.enums.StudyStatus;

import javax.persistence.*;

@Entity
@NoArgsConstructor
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
    private StudyStatus studystatus;

}
