package study.devmeetingstudy.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.boot.context.properties.bind.DefaultValue;
import study.devmeetingstudy.domain.base.BaseTimeEntity;
import study.devmeetingstudy.domain.enums.MessageStatus;
import study.devmeetingstudy.dto.message.MessageRequestDto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"member"})
@DynamicInsert
public class Message {

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
    private MessageStatus status;

    @Column(nullable = false)
    private String senderName;

    @Builder
    public Message(Long senderId, String content, String senderName, Member member){
        this.senderId = senderId;
        this.content = content;
        this.senderName = senderName;
        this.member = member;
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
                .senderName(messageRequestDto.getSender().getEmail())
                .member(messageRequestDto.getMember()).build();
    }

    public static Message changeStatus(Message message){
        message.setStatus(MessageStatus.READ);
        return message;
    }

    private void setStatus(MessageStatus messageStatus){
        this.status = messageStatus;
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
}
