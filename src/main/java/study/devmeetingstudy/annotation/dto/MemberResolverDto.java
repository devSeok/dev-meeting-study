package study.devmeetingstudy.annotation.dto;


import lombok.*;
import study.devmeetingstudy.domain.member.Member;


@Data
@AllArgsConstructor
public class MemberResolverDto {

    private Long id;
    private String nickname;

    // https://velog.io/@ljinsk3/%EC%A0%95%EC%A0%81-%ED%8C%A9%ED%86%A0%EB%A6%AC-%EB%A9%94%EC%84%9C%EB%93%9C%EB%8A%94-%EC%99%9C-%EC%82%AC%EC%9A%A9%ED%95%A0%EA%B9%8C
    public static MemberResolverDto from(Member member) {
        return new MemberResolverDto(member.getId(), member.getNickname());
    }
}
