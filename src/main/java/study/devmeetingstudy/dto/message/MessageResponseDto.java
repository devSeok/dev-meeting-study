package study.devmeetingstudy.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.message.Message;
import study.devmeetingstudy.domain.message.enums.MessageDeletionStatus;
import study.devmeetingstudy.domain.message.enums.MessageReadStatus;
import study.devmeetingstudy.dto.member.MemberResponseDto;

@Data
@AllArgsConstructor
public class MessageResponseDto {
    private Long id;
    private MemberResponseDto sender;
    private MemberResponseDto member;
    private String content;
    private MessageDeletionStatus delflg;
    private MessageReadStatus status;

    public static MessageResponseDto of(Message message, Member sender, Member member){
        return new MessageResponseDto(
                message.getId(),
                MemberResponseDto.of(sender),
                MemberResponseDto.of(member),
                message.getContent(),
                message.getDelflg(),
                message.getStatus());
    }
}
