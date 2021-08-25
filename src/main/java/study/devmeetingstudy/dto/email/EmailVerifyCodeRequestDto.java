package study.devmeetingstudy.dto.email;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class EmailVerifyCodeRequestDto {

    @NotEmpty(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    @ApiModelProperty(value = "이메일", required = true, example = "test@naver.com")
    private String email;

    @NotEmpty(message = "인증코드은 필수입니다.")
    @ApiModelProperty(value = "인증코드", required = true, example = "123-123")
    private String auth_number;
}
