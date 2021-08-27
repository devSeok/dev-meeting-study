package study.devmeetingstudy.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.devmeetingstudy.domain.member.Member;

@Getter
@AllArgsConstructor
public class MemberSignupResponseDto {

    private Long id;
    private String nickname;
    private String email;
    private int grade;

    public static MemberSignupResponseDto from(Member member) {
        return new MemberSignupResponseDto(member.getId(), member.getNickname(),member.getEmail(), member.getGrade());
    }
}
