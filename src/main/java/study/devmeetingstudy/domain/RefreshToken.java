package study.devmeetingstudy.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import study.devmeetingstudy.dto.TokenDto;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshToken {

    @Id
    @Column(name = "refresh_token_key")
    private String key;

    @Column(name = "refresh_token_value")
    private String value;

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
    public static RefreshToken createRefreshToken(Authentication authentication, TokenDto tokenDto){
        return RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
    }

    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
