package study.devmeetingstudy.dto.message;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import study.devmeetingstudy.domain.member.Member;

import javax.validation.constraints.NotEmpty;


// 일단 해당 클래스는 Message 요청시에도 생성되고,
// Message 생성자를 주입할 때도 쓰인다.
@Data
@ToString
public class MessageRequestDto {

    @NotEmpty(message = "이메일은 필수 입니다.")
    @ApiModelProperty(value = "보낼 대상 이메일")
    private String email;

    @NotEmpty(message = "메시지 내용은 필수입니다.")
    @ApiModelProperty(value = "메시지 내용", required = true, example = "하이")
    private String content;

    private Member member;

    private Member sender;

    public MessageRequestDto() {
    }

    public MessageRequestDto(String email, String content) {
        this.email = email;
        this.content = content;
    }
}
