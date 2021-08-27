package study.devmeetingstudy.domain.member;


import io.jsonwebtoken.lang.Assert;
import lombok.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.devmeetingstudy.domain.base.BaseTimeEntity;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.domain.message.Message;
import study.devmeetingstudy.dto.member.MemberSignupRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
@ToString(exclude = {"messages"})
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 30, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private int grade;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;


    @OneToMany(mappedBy = "member")
    private final List<Message> messages = new ArrayList<>();


    public static Member createMember(MemberSignupRequestDto memberRequestDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(memberRequestDto.getEmail())
                .nickname(memberRequestDto.getNickname())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .authority(Authority.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    @Builder
    public Member(Long id, String email,String nickname, String password, Authority authority, int grade, MemberStatus status) {

        Assert.notNull(email, "이메일은 필수입니다.");
        Assert.notNull(password, "비밀번호은 필수입니다.");

        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.authority = authority;
        this.grade = grade;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(getId(), member.getId()) && Objects.equals(getEmail(), member.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }

    // 참고 : set 메서드는 롬복으로 열어두면 원치 않는 값을 변경되는걸 열어두는 행위이다.
    // 필요한것만 열어두는 습관을 두자
    public void changeStatus(MemberStatus status){
        this.status = status;
    }
}

