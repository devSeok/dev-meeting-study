package study.devmeetingstudy.domain.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.domain.message.enums.MessageDeletionStatus;
import study.devmeetingstudy.domain.message.enums.MessageReadStatus;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    private Member member;
    private Member sender;

    @BeforeEach
    void injectField(){
        member = createMember(1L, "dltmddn@na.na","nick1");
        sender = createMember(2L, "sksk@sksk.sksk", "nick2");
    }

    private Member createMember(Long id, String email, String nickname){
        return Member.builder()
                .authority(Authority.ROLE_USER)
                .email(email)
                .password("1234")
                .status(MemberStatus.ACTIVE)
                .grade(0)
                .nickname(nickname)
                .id(id)
                .build();
    }

    @DisplayName("메시지 읽음 상태 수정")
    @Test
    void 메시지상태수정_수정후상태비교_True() throws Exception{
        //given


        Message message = getMessage();

        //when
        Message.changeReadStatus(MessageReadStatus.READ, message);

        //then
        assertEquals(MessageReadStatus.READ, message.getStatus());
    }

    private Message getMessage() {
        Message message = Message.builder()
                .id(1L)
                .senderId(sender.getId())
                .senderName(sender.getNickname())
                .member(member)
                .content("하이")
                .delflg(MessageDeletionStatus.NOT_DELETED)
                .status(MessageReadStatus.NOT_READ)
                .build();
        return message;
    }

    @Test
    void 메시지삭제처리_삭제후상태비교_True() throws Exception{
        //given
        Message message = getMessage();
        //when
        Message.changeDeletionStatus(MessageDeletionStatus.DELETED, message);
        //then
        assertEquals(MessageDeletionStatus.DELETED, message.getDelflg());
    }

}