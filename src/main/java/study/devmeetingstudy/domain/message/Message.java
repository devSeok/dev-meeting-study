package study.devmeetingstudy.domain.message;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import study.devmeetingstudy.domain.base.BaseTimeEntity;
import study.devmeetingstudy.domain.message.enums.MessageDeletionStatus;
import study.devmeetingstudy.domain.message.enums.MessageReadStatus;
import study.devmeetingstudy.dto.message.MessageRequestDto;
import lombok.NoArgsConstructor;
import study.devmeetingstudy.domain.member.Member;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"member"})
@DynamicInsert
public class Message extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String content;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NOT_READ'")
    private MessageReadStatus status;

    @Column(nullable = false)
    private String senderName;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NOT_DELETED'")
    private MessageDeletionStatus delflg;


    @Builder
    public Message(Long id, Long senderId, Member member, String content, MessageReadStatus status, String senderName, MessageDeletionStatus delflg) {
        this.id = id;
        this.senderId = senderId;
        this.member = member;
        this.content = content;
        this.status = status;
        this.senderName = senderName;
        this.delflg = delflg;
    }

    /**
     * PK memberId에 저장될 value는 보낼 대상 Id 이다.
     * @param messageRequestDto
     * @return
     */
    public static Message create(MessageRequestDto messageRequestDto){
        return Message.builder()
                .senderId(messageRequestDto.getSender().getId())
                .content(messageRequestDto.getContent())
                .senderName(messageRequestDto.getSender().getNickname())
                .member(messageRequestDto.getMember()).build();
    }

    public static Message changeReadStatus(MessageReadStatus status, Message message){
        message.setStatus(status);
        return message;
    }

    private void setStatus(MessageReadStatus status){
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(getId(), message.getId()) && Objects.equals(getSenderId(), message.getSenderId()) && Objects.equals(getContent(), message.getContent()) && Objects.equals(getSenderName(), message.getSenderName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSenderId(), getContent(), getSenderName());
    }

    public static Message changeDeletionStatus(MessageDeletionStatus delflg, Message message) {
        message.setDelflg(delflg);
        return null;
    }

    private void setDelflg(MessageDeletionStatus delflg) {
        this.delflg = delflg;
    }
}
