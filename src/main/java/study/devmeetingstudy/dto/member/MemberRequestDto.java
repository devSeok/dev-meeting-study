package study.devmeetingstudy.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private final static int GRADE = 0;

    @NotEmpty(message = "이메일은 필수입니다.")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호은 필수입니다.")
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}