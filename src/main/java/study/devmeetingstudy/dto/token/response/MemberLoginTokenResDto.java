package study.devmeetingstudy.dto.token.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.devmeetingstudy.dto.token.TokenDto;

@Getter
@AllArgsConstructor
public class MemberLoginTokenResDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

    public static MemberLoginTokenResDto from(TokenDto dto) {
        return new MemberLoginTokenResDto(dto.getGrantType(), dto.getAccessToken(), dto.getRefreshToken(), dto.getAccessTokenExpiresIn());
    }
}
