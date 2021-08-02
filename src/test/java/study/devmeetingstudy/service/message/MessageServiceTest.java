package study.devmeetingstudy.service.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.domain.Authority;
import study.devmeetingstudy.domain.Member;
import study.devmeetingstudy.domain.Message;
import study.devmeetingstudy.domain.UserStatus;
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

    /**
     * 일단 em 으로 진행.
     * @throws Exception
     */
    @Test
    void 메시지_생성() throws Exception{
        // given
        Member member = buildMember("xonic@na.na","1234");
        Member sender = buildMember("dltmddn@na.na", "1234");
        em.persist(member);
        em.persist(sender);
        Message message = getMessage(member,sender);
        em.persist(message);

        // when
        em.flush();
        em.clear();

        Message findMessage = em.find(Message.class, 1L);

        // then
        Assertions.assertEquals(message, findMessage);
    }

    private Member buildMember(String email, String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .authority(Authority.ROLE_ADMIN)
                .grade(0)
                .status(UserStatus.active).build();
    }

    private Message getMessage(Member member, Member sender){
        return Message.builder()
                .senderId(sender.getId())
                .content("1234")
                .senderName(sender.getEmail())
                .member(member).build();
    }





}