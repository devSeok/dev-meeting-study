package study.devmeetingstudy.domain;


import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class StudyMember {

    @Id @GeneratedValue
    @Column(name = "study_member_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
//
//    private Study study;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'MEMBER'")  // 최초 방을 생성하는 사람이 리더가 된다.
    private StudyAuth studyAuth;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'JOIN'")
    private StudyStatus studystatus;

}
