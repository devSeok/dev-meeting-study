package study.devmeetingstudy.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import study.devmeetingstudy.domain.base.BaseTimeEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Email extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Long id;

    private String email;

    private String auth_number;

    public static Email createEmail(String to, String code){
        return Email.builder()
                .email(to)
                .auth_number(code)
                .build();
    }

    @Builder
    public Email(String email, String auth_number) {
        this.email = email;
        this.auth_number = auth_number;
    }
}
