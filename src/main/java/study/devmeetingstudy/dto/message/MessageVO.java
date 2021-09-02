package study.devmeetingstudy.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.devmeetingstudy.domain.member.Member;

@Data
@AllArgsConstructor
public class MessageVO {
    private String content;
    private Member member;
    private Member sender;

    public static MessageVO of(MessageRequestDto messageRequestDto, Member member, Member sender){
        return new MessageVO(
                messageRequestDto.getContent(),
                member,
                sender
        );
    }

}
