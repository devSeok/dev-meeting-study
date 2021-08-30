package study.devmeetingstudy.dto.token.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class TokenRequestDto {

    @NotEmpty(message = "기존 accessToken 값이 있어야됩니다.")
    @ApiModelProperty(value = "액세스 토큰", required = true, example = "asd")
    private String accessToken;

    @NotEmpty(message = "기존 refreshToken 값이 있어야됩니다.")
    @ApiModelProperty(value = "리플래쉬 토큰", required = true, example = "asds")
    private String refreshToken;
}
