package study.devmeetingstudy.annotation.dto;


import lombok.*;
import study.devmeetingstudy.domain.member.Member;


@Data
public class MemberResolverDto {

    private Long id;
    private String nickname;

    public MemberResolverDto(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
    }
}
