package study.devmeetingstudy.domain;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.devmeetingstudy.domain.base.BaseTimeEntity;
import study.devmeetingstudy.dto.MemberRequestDto;

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

    @Column(length = 100,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private int grade;

    @Enumerated(EnumType.STRING)
    private UserStatus status;


    @OneToMany(mappedBy = "member")
    private final List<Message> messages = new ArrayList<>();

    @Builder
    public Member(String email, String password, Authority authority, int grade, UserStatus status) {
        this.email = email;
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
}

