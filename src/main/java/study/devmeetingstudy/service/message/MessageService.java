package study.devmeetingstudy.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.devmeetingstudy.domain.Member;
import study.devmeetingstudy.domain.Message;
import study.devmeetingstudy.dto.message.MessageRequestDto;
import study.devmeetingstudy.repository.MemberRepository;
import study.devmeetingstudy.repository.message.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageService {
    private MessageRepository messageRepository;
    private MemberRepository memberRepository;
    /**
     * 회원 인증 정보
     * @return
     */
    public Message sendMessage(MessageRequestDto messageRequestDto, Member member, Member sender){
        return Message.saveMessage(shutXss(messageRequestDto),sender,member);
    }

    /**
     * Xss 인증처리
     * @param messageRequestDto
     * @return
     */
    private MessageRequestDto shutXss(MessageRequestDto messageRequestDto){
        String content = messageRequestDto.getContent();
        return messageRequestDto;
    }
}
