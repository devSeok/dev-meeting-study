package study.devmeetingstudy.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.devmeetingstudy.domain.base.BaseTimeEntity;
import study.devmeetingstudy.dto.MemberRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private int grade;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private final List<Message> messages = new ArrayList<>();

    @Builder
    public Member(String email, String password, Authority authority, int grade, UserStatus status) {
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.grade = grade;
        this.status = status;
    }
}

