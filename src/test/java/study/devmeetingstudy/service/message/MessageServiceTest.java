package study.devmeetingstudy.service.message;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.domain.message.Message;
import study.devmeetingstudy.domain.message.enums.MessageDeletionStatus;
import study.devmeetingstudy.domain.message.enums.MessageReadStatus;
import study.devmeetingstudy.service.AuthService;

import javax.persistence.EntityManager;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@Transactional
class MessageServiceTest {

    private final EntityManager em;
    private final MessageService messageService;
    private final AuthService authService;
    Member member;
    Member sender;

    @BeforeEach
    void setMemberAndSender() {
        member = buildMember("xonic@na.na", "1234", "nick1");
        sender = buildMember("dltmddn@na.na", "1234", "nick2");
        em.persist(member);
        em.persist(sender);
    }

    private Member buildMember(String email, String password, String nickname) {
        return Member.builder()
                .email(email)
                .password(password)
                .authority(Authority.ROLE_USER)
                .grade(0)
                .status(MemberStatus.ACTIVE)
                .nickname(nickname)
                .build();
    }

    private Message getMessage() {
        Message message = Message.builder()
                .senderId(sender.getId())
                .senderName(sender.getNickname())
                .member(member)
                .content("하이")
                .delflg(MessageDeletionStatus.NOT_DELETED)
                .status(MessageReadStatus.NOT_READ)
                .build();
        return message;
    }


}