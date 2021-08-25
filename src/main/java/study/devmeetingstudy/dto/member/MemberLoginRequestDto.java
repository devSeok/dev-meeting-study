package study.devmeetingstudy.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginRequestDto {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    @Size(min = 10, max = 100, message = "이메일은 최소 10 ~ 100자 이여야됩니다.")
    @ApiModelProperty(value = "이메일", required = true, example = "test@naver.com")
    private String email;

    @NotBlank(message = "비밀번호은 필수입니다.")
    @ApiModelProperty(value = "비밀번호", required = true, example = "123456")
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
