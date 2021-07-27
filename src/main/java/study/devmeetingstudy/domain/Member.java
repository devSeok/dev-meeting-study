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

@Setter
@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private int grade;

    @Enumerated(EnumType.STRING)
    private UserStatus status;


    public static Member createMember(MemberRequestDto memberRequestDto, PasswordEncoder passwordEncoder) {
            Member member = new Member();

            member.setEmail(memberRequestDto.getEmail());
            member.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
            member.setAuthority(Authority.ROLE_USER);
            member.setStatus(UserStatus.active);

        // 회원가입시 초기 값 셋팅
        return member;
    }
}

