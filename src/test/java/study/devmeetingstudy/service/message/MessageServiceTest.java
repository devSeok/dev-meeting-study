package study.devmeetingstudy.service.message;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.common.exception.global.error.exception.MessageNotFoundException;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.domain.message.Message;
import study.devmeetingstudy.domain.message.enums.MessageDeletionStatus;
import study.devmeetingstudy.domain.message.enums.MessageReadStatus;
import study.devmeetingstudy.dto.message.MessageRequestDto;
import study.devmeetingstudy.repository.message.MessageRepository;
import study.devmeetingstudy.service.AuthService;
import study.devmeetingstudy.vo.MessageVO;

import javax.persistence.EntityManager;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    private Member member;
    private Member loginMember;

    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;


    @BeforeEach
    void init(){
        loginMember = createMember(1L, "dltmddn@na.na", "nick1");
        member = createMember(2L, "xonic@na.na", "nick2");
    }

    private Member createMember(Long id, String email, String nickname){
        return Member.builder()
                .authority(Authority.ROLE_USER)
                .email(email)
                .password("123456")
                .status(MemberStatus.ACTIVE)
                .grade(0)
                .nickname(nickname)
                .id(id)
                .build();
    }


    @DisplayName("메시지 저장")
    @Test
    void send() throws Exception{
        //given
        // 로그인 한 멤버가 멤버에게 메시지 보냄

        MessageRequestDto messageRequestDto = new MessageRequestDto("dltmddn@na.na", "하이");
        MessageVO messageVO = MessageVO.of(messageRequestDto, member, loginMember);

        // messageRepository.save로 생성된 메시지
        Message createdMessage = createMessage(1L, member, loginMember);
        // messageRepository mocking
        doReturn(createdMessage).when(messageRepository).save(any(Message.class));

        //when
        Message message = messageService.send(messageVO);

        //then
        assertNotNull(message);
    }

    private Message createMessage(Long id, Member member, Member sender) {
        return Message.builder()
                .id(id)
                .content("하이")
                .senderName(sender.getNickname())
                .senderId(sender.getId())
                .member(member)
                .delflg(MessageDeletionStatus.NOT_DELETED)
                .status(MessageReadStatus.NOT_READ).build();
    }

    @DisplayName("메시지 조회")
    @Test
    public void getMessage() throws Exception{
        //given
        Long messageId = 1L;
        Message createdMessage = createMessage(messageId, loginMember, member);

        doReturn(Optional.of(createdMessage)).when(messageRepository).findById(messageId);

        //when
        Message message = messageService.getMessage(messageId);

        //then
        assertEquals(MessageReadStatus.READ, message.getStatus());
    }

    @DisplayName("메시지 조회 MessageNotFoundException")
    @Test
    public void getMessage_messageNotFoundException() throws Exception{
        //given
        Long messageId = 1L;

        doReturn(Optional.empty()).when(messageRepository).findById(messageId);

        //when
        MessageNotFoundException messageNotFoundException = assertThrows(MessageNotFoundException.class, () -> messageService.getMessage(messageId));
        String message = messageNotFoundException.getMessage();

        //then
        assertEquals("해당 id로 메시지를 찾을 수 없습니다.", message);
    }

    @DisplayName("메시지 목록")
    @Test
    void getMessages() throws Exception{
        //given
        List<Message> createdMessages = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            createdMessages.add(Message.create(MessageVO.of(new MessageRequestDto("하이", member.getEmail()), loginMember, member)));
        }
        doReturn(createdMessages).when(messageRepository).findMessagesDesc(loginMember);

        //when
        List<Message> messages = messageService.getMessages(loginMember);

        //then
        assertEquals(5, messages.size());
    }

    @DisplayName("메시지 삭제")
    @Test
    void deleteMessage() throws Exception{
        //given
        Long messageId = 1L;
        Message message = createMessage(1L, loginMember, member);
        doReturn(Optional.of(message)).when(messageRepository).findById(messageId);

        //when

        messageService.deleteMessage(messageId);

        //then
        assertEquals(MessageDeletionStatus.DELETED, message.getDelflg());
    }
}