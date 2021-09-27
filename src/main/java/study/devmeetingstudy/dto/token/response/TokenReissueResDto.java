package study.devmeetingstudy.dto.token.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.devmeetingstudy.dto.token.TokenDto;


@Getter
@AllArgsConstructor
public class TokenReissueResDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

    public static TokenReissueResDto from(TokenDto dto) {
        return new TokenReissueResDto(dto.getGrantType(), dto.getAccessToken(), dto.getRefreshToken(), dto.getAccessTokenExpiresIn());
    }

}
