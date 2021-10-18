package study.devmeetingstudy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.common.exception.global.error.exception.MessageNotFoundException;
import study.devmeetingstudy.domain.message.Message;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.enums.DeletionStatus;
import study.devmeetingstudy.domain.message.enums.MessageReadStatus;
import study.devmeetingstudy.vo.MessageVO;
import study.devmeetingstudy.repository.message.MessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;
    /**
     * 회원 인증 정보
     * @return Message
     */
    @Transactional
    public Message sendMessage(MessageVO messageVO){
        Message createMessage = Message.create(messageVO);
        return messageRepository.save(createMessage);
    }

    @Transactional
    public Message findMessage(Long id){
        Message foundMessage = messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException("해당 id로 메시지를 찾을 수 없습니다."));

        return readMessage(foundMessage);
    }

    public Message readMessage(Message message){
        if (isNotRead(message.getStatus())) return Message.changeReadStatus(MessageReadStatus.READ, message);
        return message;
    }

    private boolean isNotRead(MessageReadStatus messageReadStatus){
        return MessageReadStatus.NOT_READ == messageReadStatus;
    }

    public List<Message> findMessages(Member member){
        return messageRepository.findMessagesDesc(member);
    }

    // 영속성 관리
    @Transactional
    public void deleteMessage(Message message) {
        if (isNotDeleted(message.getDeletionStatus())) {
            Message.changeDeletionStatus(DeletionStatus.DELETED, message);
            // 영속화 후 변경 감지.
            messageRepository.save(message);
        }
    }

    private boolean isNotDeleted(DeletionStatus deletionStatus){
        return deletionStatus == DeletionStatus.NOT_DELETED;
    }
}
