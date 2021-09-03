package study.devmeetingstudy.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.dto.message.MessageRequestDto;

@Getter
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
