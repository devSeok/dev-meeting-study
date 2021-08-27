package study.devmeetingstudy.dto.token.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.devmeetingstudy.dto.token.TokenDto;

@Getter
@AllArgsConstructor
public class MemberLoginTokenResponseDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

    public static MemberLoginTokenResponseDto from(TokenDto dto) {
        return new MemberLoginTokenResponseDto(dto.getGrantType(), dto.getAccessToken(), dto.getRefreshToken(), dto.getAccessTokenExpiresIn());
    }
}
