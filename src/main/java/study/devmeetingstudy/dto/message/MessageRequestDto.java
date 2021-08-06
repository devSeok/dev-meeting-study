package study.devmeetingstudy.dto.message;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import study.devmeetingstudy.domain.member.Member;

@Data
public class MessageRequestDto {

    private Long memberId;

    private String content;

    private Member sender;

    private Member member;

    public MessageRequestDto(Long memberId, String content) {
        this.memberId = memberId;
        this.content = content;
    }

    @Builder
    public MessageRequestDto(Long memberId, String content, Member sender, Member member) {
        this.memberId = memberId;
        this.content = content;
        this.sender = sender;
        this.member = member;
    }
}
