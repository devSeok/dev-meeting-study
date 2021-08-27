package study.devmeetingstudy.dto.member.response;


import lombok.*;
import study.devmeetingstudy.domain.member.Member;

@Getter
@AllArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String nickname;
    private String email;
    private int grade;

    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(member.getId(), member.getNickname(),member.getEmail(), member.getGrade());
    }
}
