package study.devmeetingstudy.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.devmeetingstudy.domain.Message;
import study.devmeetingstudy.domain.base.BaseTimeEntity;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.UserStatus;
import study.devmeetingstudy.dto.member.MemberRequestDto;

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

    @Column(length = 100, unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private int grade;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private final List<Message> messages = new ArrayList<>();


    public static Member createMember(MemberRequestDto memberRequestDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(memberRequestDto.getEmail())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .authority(Authority.ROLE_USER)
                .status(UserStatus.ACTIVE)
                .build();
    }

    @Builder
    public Member(String email, String password, Authority authority, int grade, UserStatus status) {
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.grade = grade;
        this.status = status;
    }
}

