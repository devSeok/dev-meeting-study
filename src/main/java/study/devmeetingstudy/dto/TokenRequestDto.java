package study.devmeetingstudy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class TokenRequestDto {

    @NotEmpty(message = "기존 accessToken 값이 있어야됩니다.")
    private String accessToken;

    @NotEmpty(message = "기존 refreshToken 값이 있어야됩니다.")
    private String refreshToken;
}
