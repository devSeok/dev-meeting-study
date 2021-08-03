package study.devmeetingstudy.service.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.domain.Authority;
import study.devmeetingstudy.domain.Member;
import study.devmeetingstudy.domain.Message;
import study.devmeetingstudy.domain.UserStatus;
import study.devmeetingstudy.dto.message.MessageRequestDto;
import study.devmeetingstudy.repository.MemberRepository;
import study.devmeetingstudy.repository.message.MessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MessageServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageService messageService;
    @Autowired
    MemberRepository memberRepository;
    Member member;
    Member sender;

    @BeforeEach
    void setMemberAndSender(){
        member = buildMember("xonic@na.na","1234");
        sender = buildMember("dltmddn@na.na", "1234");
        em.persist(member);
        em.persist(sender);
        em.flush();
        em.clear();
    }

    private Member buildMember(String email, String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .authority(Authority.ROLE_ADMIN)
                .grade(0)
                .status(UserStatus.active).build();
    }

    private Message buildMessage(Member member, Member sender){
        return Message.builder()
                .senderId(sender.getId())
                .content("1234")
                .senderName(sender.getEmail())
                .member(member).build();
    }


    @Test
    void 메시지생성_객체가같은지_True(){
        // given

        Message memberToSenderMessage = messageService.sendMessage(getMessageDto(sender), member, sender);
        Message senderToMemberMessage = messageService.sendMessage(getMessageDto(member), sender, member);

        // when
//        Message findMessage = em.find(Message.class, 1L);

        // then
//        Assertions.assertEquals(message, findMessage);
    }

    private MessageRequestDto getMessageDto(Member member){
        if (this.member == member) member = this.member;
        else member = this.sender;
        return createMessage(member);
    }

    private MessageRequestDto createMessage(Member member){
        return new MessageRequestDto(sender.getId(), "하이");
    }

}