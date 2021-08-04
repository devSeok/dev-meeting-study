package study.devmeetingstudy.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.common.exception.global.error.exception.MessageNotFoundException;
import study.devmeetingstudy.domain.Member;
import study.devmeetingstudy.domain.Message;
import study.devmeetingstudy.dto.message.MessageRequestDto;
import study.devmeetingstudy.repository.MemberRepository;
import study.devmeetingstudy.repository.message.MessageRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    /**
     * 회원 인증 정보
     * @return Message
     */
    @Transactional
    public Message save(MessageRequestDto messageRequestDto){
        Message message = Message.create(shutXss(messageRequestDto));
        return messageRepository.save(message);
    }

    /**
     * Xss 인증처리
     */
    private MessageRequestDto shutXss(MessageRequestDto messageRequestDto){
        String content = messageRequestDto.getContent();
        return messageRequestDto;
    }

    public Message getMessage(Long id){
        return messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException("해당 id로 메시지를 찾을 수 없습니다."));
    }

    public List<Message> getMessages(Member member){
        return messageRepository.findMessagesDesc(member);
    }


}
