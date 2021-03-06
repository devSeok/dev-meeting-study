package study.devmeetingstudy.dto.member.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import study.devmeetingstudy.domain.member.Member;

@Getter
@AllArgsConstructor
public class MemberSignupResDto {

    @ApiModelProperty(value = "멤버 아이디", example = "1", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "멤버 닉네임", example = "xonic")
    private String nickname;

    @ApiModelProperty(value = "멤버 이메일", example = "xonic@naver.com")
    private String email;

    @ApiModelProperty(value = "멤버 매너 점수", example = "5")
    private Integer grade;

    public static MemberSignupResDto from(Member member) {
        return new MemberSignupResDto(member.getId(), member.getNickname(),member.getEmail(), member.getGrade());
    }
}
